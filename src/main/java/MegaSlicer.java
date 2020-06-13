package main.java;

import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MegaSlicer extends SuperSlicer {
    public MegaSlicer(Point point, List<Point> polyline) {
        super(point, polyline);
        super.setImage(new Image("res/images/megaslicer.png"));
        super.setSpeed(super.getSpeed());
        super.setHealth(super.getHealth()*2);
        super.setReward(10);
        super.setPenalty(super.getPenalty()*2);
        super.setChildToSpawn(new SuperSlicer(point, polyline));
        super.setNumToSpawn(2);
    }


}


