package main.java;

import bagel.util.Point;

import java.util.List;

public class ApexSlicer extends MegaSlicer {
    private final double speed = super.getSpeed() / 2;
    private int health = 25;
    private int reward = 150;
    private int penalty = super.getPenalty()*4;

    public ApexSlicer(String imageFile) {
        super(imageFile);
    }
}
