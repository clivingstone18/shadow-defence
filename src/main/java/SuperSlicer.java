package main.java;
import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

public class SuperSlicer extends Slicer implements Spawnable {
    private int numToSpawn;
    private final double SPEED_FACTOR = 0.75;
    private final int SUPERSLICER_REWARD = 15;
    private final int PENALTY_FACTOR = 2;
    private final int HEALTH_FACTOR = 1;
    private final int NUM_TO_SPAWN = 2;
    private final String IMG_STR = "res/images/superslicer.png";
    private Slicer childToSpawn;

    public SuperSlicer(Point point, List<Point> polyline) {
        super(point, polyline);
        super.setImage(new Image(IMG_STR));
        super.setSpeed(super.getSpeed()*SPEED_FACTOR);
        super.setHealth(super.getHealth()*HEALTH_FACTOR);
        super.setReward(SUPERSLICER_REWARD);
        super.setPenalty(super.getPenalty()*PENALTY_FACTOR);
        this.numToSpawn = NUM_TO_SPAWN;
        this.childToSpawn = new Slicer(point, polyline);
    }

    public void setNumToSpawn(int numToSpawn) {
        this.numToSpawn = numToSpawn;
    }

    public void setChildToSpawn(Slicer childToSpawn) {
        this.childToSpawn = childToSpawn;
    }

    public List<Slicer> getChildrenToSpawn() {
        List<Slicer> childrenToSpawn = new ArrayList<>();
        Point point = getCenter();
        for (int i=0; i<numToSpawn; i++) {
            Slicer child = childToSpawn.copy();
            child.centerRectAt(point);
            child.setTargetPointIndex(getTargetPointIndex());
            childrenToSpawn.add(child);
        }
        return childrenToSpawn;
    }

}
