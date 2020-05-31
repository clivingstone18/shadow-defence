package main.java;

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
        if (type.equals("slicer")) {
            return new Slicer("res/images/slicer.png");
        } else if (type.equals("superslicer")) {
            return new SuperSlicer("res/images/superslicer.png");
        } else if (type.equals("megaslicer")) {
            return new MegaSlicer("res/images/superslicer.png");
        } else {
            return new ApexSlicer("res/images/apexslicer.png");
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


