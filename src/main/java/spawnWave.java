package main.java;

import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class spawnWave extends Wave {
    private boolean isHappening;
    private int waveNo;

    private int slicerCount;
    private int spawnedSlicers;
    private int slicersFinished;
    private int frameCount;
    private String enemyType;
    private double delay;
    private boolean hasFinished;
    private double currTime;
    private double duration;

    private final double FPS = 60;

    private List<Slicer> activeSlicers;

    public double getDuration() {
        return duration;
    }

    public double getCurrTime() {
        return currTime;
    }

    public int getWaveNo() {
        return waveNo;
    }
    //constructors
    public spawnWave(int waveNo, int slicerCount, String enemyType, double delay) {
        this.waveNo = waveNo;
        this.slicerCount = slicerCount;
        this.enemyType = enemyType;
        this.delay = delay / 1000;
        this.frameCount = 0;
        this.isHappening = false;
        this.duration = (slicerCount-1) * this.delay;
    }

    public boolean isHappening() {
        return isHappening;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Slicer slicerOfType(String type) {
        Point init = Map.getPolylinePoints().get(0);
        if (type.equals("slicer")) {
            return new Slicer("res/images/slicer.png", 2, 1, 2, 1, init);
        } else if (type.equals("superslicer")) {
            return new SuperSlicer("res/images/superslicer.png", 0.75*2, 1, 15, 4, init);
        } else if (type.equals("megaslicer")) {
            return new MegaSlicer("res/images/megaslicer.png", 0.75*2, 2, 10,  8, init);
        } else {
            return new ApexSlicer("res/images/apexslicer.png", 0.75, 25, 150,  32, init);
        }
    }

    //starts the wave of the game
    public void Start() {
        activeSlicers = new ArrayList<>();
        Slicer newSlicer = slicerOfType(enemyType);
        activeSlicers.add(newSlicer);
        this.isHappening = true;
        this.spawnedSlicers = 1;
        this.hasFinished=false;
        this.currTime = 0;
        ShadowDefend.activeEnemies.add(new Enemy(newSlicer));
    }

    @Override
    public void Update() {
        frameCount += ShadowDefend.getTimescale();
        currTime += ShadowDefend.getTimescale() / FPS;
        for (int i = 0; i < activeSlicers.size(); i++) {
            if (frameCount/FPS >= delay && spawnedSlicers != slicerCount) {
                Slicer newSlicer = slicerOfType(enemyType);
                activeSlicers.add(newSlicer);
                ShadowDefend.activeEnemies.add(new Enemy(newSlicer));
                spawnedSlicers++;
                frameCount = 0;
            }
        }
        // Check for slicers that have finished
        for (int i = 0; i < activeSlicers.size(); i++) {
            if (activeSlicers.get(i) != null && activeSlicers.get(i).isFinished()) {
                slicersFinished++;
                activeSlicers.remove(activeSlicers.get(i));
            }
        }

        if (slicersFinished == slicerCount) {
            hasFinished = true;
        }
    }
}


