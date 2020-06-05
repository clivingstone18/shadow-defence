package main.java;

import bagel.util.Point;

public interface Attackable {
    public boolean isEliminated();
    public void reduceHealth(int towerDamage);
    public void update();
    public Point getCenter();
}