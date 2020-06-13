package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Explosive extends Projectile {
    private double effectRadius;
    private Rectangle effectRect;
    private double detTime;

    public void increaseTime() {
        detTime += ShadowDefend.getTimescale()/60;
    }

    public Explosive(Point point) {
        super(point, 500);
        super.setImage(new Image("res/images/explosive.png"));
        effectRadius = 200;
        effectRect = new Rectangle(this.getCenter().x-effectRadius, this.getCenter().y-effectRadius,
                effectRadius, effectRadius);
        super.setRect(effectRect);
        detTime = 0;
    }

    public Projectile copy() {
        Projectile copy = new Explosive(this.getCenter());
        return copy;
    }
    public void updateProjectile() {
        super.update();
    }
    public double getDetTime() {
        return detTime;
    }

}
