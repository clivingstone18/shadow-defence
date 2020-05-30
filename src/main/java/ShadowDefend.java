package main.java;

import bagel.*;
import bagel.util.Vector2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.Scanner;

class ShadowDefend extends AbstractGame {

    private Map map;
    private buyPanel buyPanel;
    private statusPanel statusPanel;
    private int playerFunds;
    private int lives;
    private int currWaveIndex;
    private Wave wave;
    private Font font;
    public static double timescaleMultiplier;
    private List<Wave> waveEvents;

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
                    delayWave delayWave = new delayWave(delay/timescaleMultiplier);
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
        timescaleMultiplier = 1;
        lives = 25;
        waveEvents = readWaveEvents("res/levels/waves.txt");
    }

    @Override
    protected void update(Input input) {
        //basic rendering
        map.render();
        buyPanel.render();
        buyPanel.updateBuyPanel(playerFunds);
        statusPanel.render(currWaveIndex + 1, timescaleMultiplier,
                "Wave in Progress", lives);

        //starts the game
        if (input.wasPressed(Keys.S) && !waveEvents.get(0).isHappening()) {
            waveEvents.get(0).Start();
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
                    }
                }
                waveEvents.get(i).Update();
            }
            else if (waveEvents.get(i).hasFinished()) {
                waveEvents.remove(waveEvents.get(i));
            }
        }
    }


    public static void main(String[] args) {
        new ShadowDefend().run();

    }
}


