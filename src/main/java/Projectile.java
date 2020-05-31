package main.java;

import bagel.Image;
import bagel.util.Point;

public abstract class Projectile extends Sprite {
    private Image image;
    private double velocity;
    private double damage;

    public Projectile(Point point, String imgSrc, double velocity, double damage) {
        super(point, imgSrc);
        this.velocity = velocity;
        this.damage = damage;
    }
}

