package main.java;

import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ApexSlicer extends MegaSlicer {

    public ApexSlicer(String imgSrc, Point point) {
        super(imgSrc, point);
        super.setSpeed(super.getSpeed()/2);
        super.setHealth(25);
        super.setReward(150);
        super.setPenalty(super.getPenalty()*2);
        super.setNumToSpawn(4);
        super.setChildToSpawn(new MegaSlicer("res/images/megaslicer.png", point));
    }

}
