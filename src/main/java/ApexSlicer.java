package main.java;

import bagel.Image;
import bagel.util.Point;

import java.util.List;

public class ApexSlicer extends MegaSlicer {
    public ApexSlicer(Point point, List<Point> polyline) {
        super(point, polyline);
        super.setImage(new Image("res/images/apexslicer.png"));
        super.setChildToSpawn(new MegaSlicer(point, polyline));
        super.setSpeed(super.getSpeed()/2);
        super.setHealth(25);
        super.setReward(150);
        super.setPenalty(super.getPenalty()*2);
        super.setNumToSpawn(4);
    }

}
