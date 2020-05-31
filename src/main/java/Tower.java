package main.java;

import bagel.util.Point;

public abstract class Tower extends Sprite implements Cloneable {
    private int effectRadius;
    private int damage;
    private double cooldown;
    private int cost;

    public Tower(Point point, String imageSrc) {
        super(point, imageSrc);
    }

    public static Tower createNewTower(Tower tower) {
        return tower;
    }

    @Override
    public Tower clone() {
        try {
            return (Tower) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getEffectRadius() {
        return effectRadius;
    }

    public int getDamage() {return damage;}

    public int getCost() {
        return cost;
    }

    public abstract double getCooldown();
}
