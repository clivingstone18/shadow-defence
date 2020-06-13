package main.java;

import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MegaSlicer extends SuperSlicer {

    private int numToSpawn;
    private final double SPEED_FACTOR = 1;
    private final int MEGASLICER_REWARD = 10;
    private final int PENALTY_FACTOR = 2;
    private final int NUM_TO_SPAWN = 2;
    private final int HEALTH_FACTOR = 1;
    private final String IMG_STR = "res/images/megaslicer.png";

    public MegaSlicer(Point point, List<Point> polyline) {
        super(point, polyline);
        super.setImage(new Image(IMG_STR));
        super.setSpeed(super.getSpeed()*SPEED_FACTOR);
        super.setHealth(super.getHealth()*HEALTH_FACTOR);
        super.setReward(MEGASLICER_REWARD);
        super.setPenalty(super.getPenalty()*PENALTY_FACTOR);
        super.setChildToSpawn(new SuperSlicer(point, polyline));
        super.setNumToSpawn(NUM_TO_SPAWN);
    }


}


