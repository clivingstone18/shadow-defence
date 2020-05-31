package main.java;

import bagel.util.Point;

public class Attacker<T extends Tower> {
    private Point position;
    private T tower;

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setTower(T tower) {
        this.tower = tower;
    }

    public Tower getTower() {
        return tower;
    }

    public Point getPosition() {
        return position;
    }


}
