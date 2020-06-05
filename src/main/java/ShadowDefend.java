package main.java;

import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.Input;

import java.io.FileNotFoundException;
import java.util.*;

import java.io.File;

class ShadowDefend extends AbstractGame {

    private Map map;
    private buyPanel buyPanel;
    private statusPanel statusPanel;
    private String status;
    public static int lives;
    private int currWaveIndex;
    private int currWaveCount;
    private int wavesFinished;
    private boolean itemSelected;
    private Tower selectedTower;
    private Vector2 horizontal = new Vector2(1, 0);
    private Vector2 vertical = new Vector2(0, 1);
    private Vector2 planeDir;
    public static int playerFunds;
    private Font font;
    public static double timescaleMultiplier;
    private List<Wave> waveEvents;
    private List<Tower> activeTowers;
    public static List<Slicer> activeEnemies;
    public Image buyPanelBackground;
    public Image statusPanelBackground;
    public Point buyPanelCenter;
    public Point statusPanelCenter;
    private int totalWaves;
    private int level;

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
                totalWaves = waveNo;
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
                    delayWave delayWave = new delayWave(delay / timescaleMultiplier, waveNo);
                    waveEvents.add(delayWave);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file");
            e.printStackTrace();
        }
        return waveEvents;
    }

    public void switchPlaneDirVec() {
        if (planeDir.equals(horizontal)) {
            planeDir = vertical;
        } else {
            planeDir = horizontal;
        }
    }

    public ShadowDefend() {
        initialise(1);
    }

    public boolean ValidReleasePoint(Point point) {
        return !buyPanel.inBoundingBoxRange(point) && !statusPanel.inBoundingBoxRange(point) &&
                !Map.map.hasProperty((int) point.x, (int) point.y, "blocked") &&
                !inTowerRange(point);
    }

    public boolean inTowerRange(Point point) {
        for (Tower tower: activeTowers) {
            if (tower.inBoundingBoxRange(point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void update(Input input) {
        //basic rendering
        map.render();
        buyPanel.render(playerFunds);
        statusPanel.render(currWaveCount, status, lives);

        //Tracks the waves
        List<Wave> events = new ArrayList<>();
        for (int i = 0; i < waveEvents.size(); i++) {
            if (waveEvents.get(i).hasCompleted()) {
                events.add(waveEvents.get(i));
                if ((i + 1 == waveEvents.size() || waveEvents.get(i).getWaveNo() < waveEvents.get(i + 1).getWaveNo())) {
                    playerFunds += 150 + waveEvents.get(i).getWaveNo() * 100;
                    wavesFinished++;
                }//checks if wave is in progress
            } else if (waveEvents.get(i).isHappening()) {
                if (waveEvents.get(i).getDuration() <= waveEvents.get(i).getCurrTime()) {
                    if (i + 1 != waveEvents.size() && !waveEvents.get(i + 1).isHappening()) {
                        waveEvents.get(i + 1).Start();
                        currWaveCount = waveEvents.get(i + 1).getWaveNo();
                    }
                }
                waveEvents.get(i).Update();
            }
        }
        waveEvents.removeAll(events);

        //checks what happens if left clicked
        if (input.wasPressed(MouseButtons.LEFT)) {
            Point clickPoint = input.getMousePosition();
            //checks through each of the purchase items to see if user clicked them
            for (purchaseItem item : buyPanel.getPurchaseItems()) {
                if (item.getTower().inBoundingBoxRange(clickPoint)) {
                    if (item.getTower().getCost() <= playerFunds) {
                        itemSelected = true;
                        selectedTower = item.getTower().copy();
                        status = "Placing";
                    }
                }
            }
        }
        //checks whether item has been selected by the user, and if it has, updates where it will be placed
        if (itemSelected) {
            Point hoverPoint = input.getMousePosition();
            //Checks whether the item is hovering at a valid position (image rendered at user's mouse)
            if (ValidReleasePoint(hoverPoint)) {
                selectedTower.update(hoverPoint);
                if (input.wasReleased(MouseButtons.LEFT)) {
                    // Adds new general class attacker
                    Tower droppedTower = selectedTower.copy();
                    droppedTower.centerRectAt(hoverPoint);
                    if (droppedTower instanceof Airplane) {
                        Airplane airplane = (Airplane) droppedTower;
                        airplane.setDirVec(planeDir);
                        airplane.setSpawnPoint();
                        airplane.setAngle(3.14 / 2 - Math.atan2(planeDir.y, planeDir.x));
                        switchPlaneDirVec();
                    }
                    activeTowers.add(droppedTower);
                    selectedTower = null;
                    itemSelected = false;
                    status = "Wave in Progress";
                    // Updates the player's funds
                    playerFunds -= droppedTower.getCost();
                }
            }
            // Cancels the selection process
            if (input.wasPressed(MouseButtons.RIGHT)) {
                selectedTower = null;
                itemSelected = false;
            }
            // Checks whether the user is dropping the tower at a valid position
        }

        List<Airplane> finishedAirplanes = new ArrayList<>();
        for (int i = 0; i < activeTowers.size(); i++) {
            Tower tower = activeTowers.get(i);
            tower.updateAllProjectiles();
            tower.detectAndShoot();
            tower.HitTargets();
            tower.update();
            if (tower instanceof Airplane) {
                Airplane airplane = (Airplane) tower;
                if (airplane.isFinished()) {
                    finishedAirplanes.add(airplane);
                }
            }
        }
        activeTowers.removeAll(finishedAirplanes);
        ListIterator<Slicer> listIterator = activeEnemies.listIterator();
        List<Slicer> toDelete = new ArrayList<>();
        List<Slicer> newEnemies = new ArrayList<>();

        while (listIterator.hasNext()) {
            Slicer enemy = (Slicer) listIterator.next();
            enemy.update();
            if (enemy.isFinished()) {
                lives -= enemy.getPenalty();
                toDelete.add(enemy);
            } else if (enemy.isEliminated()) {
                toDelete.add(enemy);
                if (enemy instanceof Spawnable) {
                    Spawnable spawnable = (Spawnable) enemy;
                    newEnemies.addAll(spawnable.getChildrenToSpawn());
                }
            }
        }
        activeEnemies.removeAll(toDelete);
        activeEnemies.addAll(newEnemies);

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

        if (lives <= 0) {
            Window.close();
        }

        if (lives > 0 && wavesFinished == totalWaves && activeEnemies.size()==0) {
            status = "Winner!";
            System.out.println(level);
            initialise(2);
        }
        //Updates each of the enemies
    }

    public void initialise(int level) {
        map = new Map(level);
        playerFunds = 5000;
        font = new Font("res/fonts/DejaVuSans-Bold.ttf", 18);
        //read in files
        currWaveIndex = 0;
        currWaveCount = 1;
        timescaleMultiplier = 0.5;
        lives = 25;
        waveEvents = readWaveEvents("res/levels/waves.txt");
        activeTowers = new ArrayList<>();
        activeEnemies = new ArrayList<>();
        status = "Awaiting Start";
        planeDir = horizontal;
        buyPanelBackground = new Image("res/images/buypanel.png");
        statusPanelBackground = new Image("res/images/statuspanel.png");
        buyPanelCenter = new Point(buyPanelBackground.getWidth() / 2, buyPanelBackground.getHeight() / 2);
        statusPanelCenter = new Point(statusPanelBackground.getWidth() / 2,
                Window.getHeight() - statusPanelBackground.getHeight() / 2);
        buyPanel = new buyPanel(buyPanelBackground, buyPanelCenter);
        statusPanel = new statusPanel(statusPanelBackground, statusPanelCenter);
        wavesFinished = 0;
    }


    public static void main(String[] args) {
        new ShadowDefend().run();
    }
}


