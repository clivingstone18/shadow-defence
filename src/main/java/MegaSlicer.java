package main.java;

import bagel.util.Point;

import java.util.List;

public class MegaSlicer extends SuperSlicer {
    private final double speed = super.getSpeed();
    private int health = super.getHealth() * 2;
    private int reward = 10;
    private int penalty = super.getPenalty() * 2;

    public MegaSlicer(String imageFile) {
        super(imageFile);
    }
}
