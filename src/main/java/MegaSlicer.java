package main.java;

import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MegaSlicer extends SuperSlicer {

    public MegaSlicer(String imgSrc, Point point) {
        super(point, new Image(imgSrc));
        super.setSpeed(super.getSpeed());
        super.setHealth(super.getHealth() * 2);
        super.setReward(10);
        super.setPenalty(super.getPenalty() * 2);
        super.setNumToSpawn(2);
        super.setChildToSpawn(new SuperSlicer(point, new Image("res/images/superslicer.png")));
    }


}


