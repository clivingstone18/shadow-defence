package main.java;

import bagel.util.Point;
import bagel.util.Rectangle;

public class Explosive extends Projectile {
    private Point point;
    private String imgSrc;
    private double damage;
    private double effectRadius;
    private Rectangle effectRect;
    private double detTime;

    public void increaseTime() {
        detTime += ShadowDefend.getTimescale()/60;
    }

    public Explosive(Point point, String imgSrc, double effectRadius) {
        super(point, imgSrc, 500);
        effectRect = new Rectangle(this.getCenter().x-effectRadius, this.getCenter().y-effectRadius,
                effectRadius, effectRadius);
        super.setRect(effectRect);
        detTime = 0;
    }

    public void updateProjectile() {
        super.update();
    }

    public double getDetTime() {
        return detTime;
    }

    public void setDetTime(double detTime) {
        this.detTime = detTime;
    }
}
