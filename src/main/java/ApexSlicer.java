package main.java;

import bagel.Image;
import bagel.util.Point;

import java.util.List;

public class ApexSlicer extends MegaSlicer {
    private final double SPEED_FACTOR = 0.5;
    private final int APEXSLICER_REWARD = 150;
    private final int PENALTY_FACTOR = 4;
    private final int APEXSLICER_HEALTH = 25;
    private final int NUM_TO_SPAWN = 4;
    private final String IMG_STR = "res/images/apexslicer.png";

    public ApexSlicer(Point point, List<Point> polyline) {
        super(point, polyline);
        super.setImage(new Image(IMG_STR));
        super.setChildToSpawn(new MegaSlicer(point, polyline));
        super.setSpeed(super.getSpeed()*SPEED_FACTOR);
        super.setHealth(APEXSLICER_HEALTH);
        super.setReward(APEXSLICER_REWARD);
        super.setPenalty(super.getPenalty()*PENALTY_FACTOR);
        super.setNumToSpawn(NUM_TO_SPAWN);
    }

}
