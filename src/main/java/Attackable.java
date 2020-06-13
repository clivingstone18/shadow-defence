package main.java;

import bagel.util.Point;

public interface Attackable {
    boolean isEliminated();
    void reduceHealth(int towerDamage);
    void update();
    Point getCenter();
}