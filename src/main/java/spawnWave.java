package main.java;

import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class spawnWave extends Wave {
    private boolean isHappening;
    private int waveNo;
    private int slicerCount;
    private int spawnedSlicers;
    private int slicersCompleted;
    private int frameCount;
    private String enemyType;
    private double delay;
    private boolean hasFinished;
    private double currTime;
    private double duration;
    private int childSlicers;
    private List<Point> polyline;
    private int penaltiesIncurred;
    private int rewards;
    private final double FPS = 60;
    private List<Slicer> newEnemies;

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

    public int getPenaltiesIncurred() {
        return penaltiesIncurred;
    }

    public int getRewards() {
        return rewards;
    }


    //constructors
    public spawnWave(int waveNo, int slicerCount, String enemyType, double delay, List<Point> polyline) {
        this.waveNo = waveNo;
        this.slicerCount = slicerCount;
        this.enemyType = enemyType;
        this.delay = delay / 1000;
        this.frameCount = 0;
        this.isHappening = false;
        this.duration = (slicerCount - 1) * this.delay;
        this.childSlicers = 0;
        this.polyline = polyline;
        this.newEnemies = new ArrayList<>();
    }

    public boolean isHappening() {
        return isHappening;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Slicer slicerOfType(String type) {
        Point init = polyline.get(0);
        if (type.equals("slicer")) {
            return new Slicer(init, polyline);
        } else if (type.equals("superslicer")) {
            return new SuperSlicer(init, polyline);
        } else if (type.equals("megaslicer")) {
            return new MegaSlicer(init, polyline);
        } else {
            return new ApexSlicer(init, polyline);
        }
    }

    //starts the wave of the game
    public void Start() {
        activeSlicers = new ArrayList<>();
        Slicer newSlicer = slicerOfType(enemyType);
        activeSlicers.add(newSlicer);
        this.isHappening = true;
        this.spawnedSlicers = 1;
        this.hasFinished = false;
        this.currTime = 0;
        newEnemies.add(newSlicer);
    }

    @Override
    public void Update() {
        List<Slicer> newEnemies = new ArrayList<>();

        penaltiesIncurred = 0;
        rewards = 0;
        if (slicersCompleted == slicerCount + childSlicers) {
            hasFinished = true;
            return;
        }

        frameCount += ShadowDefend.getTimescale();
        currTime += ShadowDefend.timescaleMultiplier / FPS;
        //checks for new slicers
        for (int i = 0; i < activeSlicers.size(); i++) {
            if (frameCount / FPS >= delay && spawnedSlicers != slicerCount) {
                Slicer newSlicer = slicerOfType(enemyType);
                newEnemies.add(newSlicer);
                spawnedSlicers++;
                frameCount = 0;
            }
        }
        // Update the slicers
        List<Slicer> toDelete = new ArrayList<>();
        //Check for slicers that have been eliminated and spawn their children
        for (int i = 0; i < activeSlicers.size(); i++) {
            if (activeSlicers.get(i).isEliminated()) {
                if (activeSlicers.get(i) instanceof Spawnable) {
                    Spawnable spawnable = (Spawnable) activeSlicers.get(i);
                    newEnemies.addAll(spawnable.getChildrenToSpawn());
                    childSlicers += spawnable.getChildrenToSpawn().size();
                }
                toDelete.add(activeSlicers.get(i));
                slicersCompleted++;
                rewards += activeSlicers.get(i).getReward();
            }
            //Checks if slicer is finished
            else if (activeSlicers.get(i).isFinished()) {
                slicersCompleted++;
                toDelete.add(activeSlicers.get(i));
                penaltiesIncurred += activeSlicers.get(i).getPenalty();
            }
            //Just update the slicer's position
            else {
                activeSlicers.get(i).update();
            }
        }
        activeSlicers.removeAll(toDelete);
        activeSlicers.addAll(newEnemies);
    }

    public List<Slicer> getActiveSlicers() {
        return activeSlicers;
    }
}
