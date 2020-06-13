package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuperSlicer extends Slicer implements Spawnable {
    private int numToSpawn;
    private Slicer childToSpawn;

    public SuperSlicer(Point point, List<Point> polyline) {
        super(point, polyline);
        super.setImage(new Image("res/images/superslicer.png"));
        super.setSpeed(super.getSpeed()*0.75);
        super.setHealth(super.getHealth());
        super.setReward(15);
        super.setPenalty(super.getPenalty()*2);
        numToSpawn = 2;
        childToSpawn = new Slicer(point, polyline);
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
