package main.java;

import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import bagel.Input;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.Scanner;

class ShadowDefend extends AbstractGame {

    private Map map;
    private buyPanel buyPanel;
    private statusPanel statusPanel;
    private String status;
    public static int lives;
    private int currWaveIndex;
    private int currWaveCount;
    private boolean itemSelected;
    private Tower selectedTower;
    private Wave wave;
    public static int playerFunds;
    private Font font;
    public static double timescaleMultiplier;
    private List<Wave> waveEvents;
    private List<Attacker> activeTowers;
    public static List<Enemy> activeEnemies;

    public static double getTimescale() {
        return timescaleMultiplier;
    }

    //reads the waves from the text file
    public List<Wave> readWaveEvents(String filename) {
        waveEvents = new ArrayList<>();
        try {
            File files = new File(filename);
            Scanner myReader = new Scanner(files);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataArr = data.split(",");
                int waveNo = Integer.parseInt(dataArr[0]);
                //creates new spawning event
                if (dataArr[1].equals("spawn")) {
                    int slicerCount = Integer.parseInt(dataArr[2]);
                    String enemyType = dataArr[3];
                    int delay = Integer.parseInt(dataArr[4]);
                    spawnWave spawnWave = new spawnWave(waveNo, slicerCount, enemyType, delay);
                    waveEvents.add(spawnWave);
                }
                //creates new delay event
                else {
                    int delay = Integer.parseInt(dataArr[2]);
                    delayWave delayWave = new delayWave(delay/timescaleMultiplier, waveNo);
                    waveEvents.add(delayWave);
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not read file");
            e.printStackTrace();
        }
        return waveEvents;
    }

    public ShadowDefend() {
        map = new Map();
        playerFunds = 500;
        buyPanel = new buyPanel();
        statusPanel = new statusPanel();
        font = new Font("res/fonts/DejaVuSans-Bold.ttf", 18);
        //read in files
        currWaveIndex = 0;
        currWaveCount = 1;
        timescaleMultiplier = 1;
        lives = 25;
        waveEvents = readWaveEvents("res/levels/waves.txt");
        activeTowers = new ArrayList<>();
        activeEnemies = new ArrayList<>();
        status = "Awaiting Start";
    }

    public boolean ValidReleasePoint(Point point) {
        return !Sprite.cursorInRange(point, buyPanel.getBackground().getBoundingBox()) &&
                !Sprite.cursorInRange(point, statusPanel.getBackground().getBoundingBox()) &&
                !Map.map.hasProperty((int) point.x, (int) point.y, "blocked");
    }

    @Override
    protected void update(Input input) {

        //basic rendering
        map.render();
        buyPanel.render(playerFunds);
        statusPanel.render(currWaveCount, timescaleMultiplier,
                status, lives);

        //click

        if (input.wasPressed(MouseButtons.LEFT)) {
            Point clickPoint = input.getMousePosition();
            for (int i = 0; i < buyPanel.getPurchaseItems().size(); i++) {
                //add abstraction here
                if (Sprite.cursorInRange(clickPoint, buyPanel.getPurchaseItems().get(i).getTower().getRect())) {
                    if (buyPanel.getPurchaseItems().get(i).getTower().getCost() <= playerFunds) {
                        System.out.println("Selected item");
                        itemSelected = true;
                        selectedTower = buyPanel.getPurchaseItems().get(i).getTower();
                        status = "Placing";
                    }
                }
            }
        }
        if (itemSelected) {
            Point hoverPoint = input.getMousePosition();
            if (ValidReleasePoint(hoverPoint)) {
                selectedTower.update(hoverPoint);
            }
            // Cancellation
            if (input.wasPressed(MouseButtons.RIGHT)) {
                selectedTower = null;
                itemSelected = false;
            }
            if (input.wasReleased(MouseButtons.LEFT)) {
                if (ValidReleasePoint(hoverPoint)) {
                    Attacker droppedTower = new Attacker(hoverPoint, selectedTower.clone());
                    activeTowers.add(droppedTower);
                    selectedTower = null;
                    itemSelected = false;
                    status = "Wave in Progress";
                    playerFunds -= droppedTower.getTower().getCost();
                }
            }
        }

        for (Attacker attacker : activeTowers) {
            attacker.getTower().update(attacker.getPosition());
            attacker.detectAndShoot(activeEnemies);
            if (attacker.getProjectile() != null) {
                attacker.getProjectile().update();
                if (attacker.hasHitTarget()) {
                    System.out.println("hit the target");
                    attacker.deleteProjectile();
                }
            }
        }

            //starts the game
            if (input.wasPressed(Keys.S) && !waveEvents.get(0).isHappening()) {
                waveEvents.get(0).Start();
                status = "Wave in Progress";
            }

            //update timescale multiplier
            if (input.wasPressed(Keys.K) || input.wasPressed(Keys.L)) {
                if (input.wasPressed(Keys.L)) {
                    timescaleMultiplier++;
                } else {
                    if (timescaleMultiplier > 1) {
                        timescaleMultiplier--;
                    }
                }
            }

            //array of events
            for (int i = 0; i < waveEvents.size() - 1; i++) {
                //checks if wave is in progress
                if (waveEvents.get(i).isHappening()) {
                    if (waveEvents.get(i).getDuration() <= waveEvents.get(i).getCurrTime()) {
                        if (!waveEvents.get(i + 1).isHappening()) {
                            waveEvents.get(i + 1).Start();
                            currWaveCount = waveEvents.get(i + 1).getWaveNo();
                        }
                    }
                    waveEvents.get(i).Update();
                } else if (waveEvents.get(i).hasFinished()) {
                    waveEvents.remove(waveEvents.get(i));
                }
            }

        for(int i=0; i<activeEnemies.size(); i++) {
            if (activeEnemies.get(i).isEliminated()) {
                activeEnemies.remove(activeEnemies.get(i));
                continue;
            }
            activeEnemies.get(i).getEnemyType().update();
        }

}



    public static void main(String[] args) {
        new ShadowDefend().run();

    }
}


