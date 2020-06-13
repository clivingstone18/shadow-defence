package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Explosive extends Projectile {
    private double effectRadius;
    private Rectangle effectRect;
    private double detTime;
    private final double DAMAGE = 500;
    private final String IMG_FILE = "res/images/explosive.png";
    private final int EFFECT_RADIUS = 200;

    /**
     * Updates the time that it spends on the map
     */
    public void increaseTime() {
        detTime += ShadowDefend.getTimescale()/60;
    }

    public Explosive(Point point) {
        super(point);
        super.setDamage(DAMAGE);
        super.setImage(new Image(IMG_FILE));
        effectRect = new Rectangle(this.getCenter().x-EFFECT_RADIUS, this.getCenter().y-EFFECT_RADIUS,
                EFFECT_RADIUS, EFFECT_RADIUS);
        super.setRect(effectRect);
        detTime = 0;
    }

    /**
     * Creates a deep copy of the explosive
     */
    public Explosive copy() {
        Explosive copy = new Explosive(this.getCenter());
        return copy;
    }

    /**
     * Updates the position of the projectile on the map
     */
    public void updateProjectile() {
        super.update();
    }

    public double getDetTime() {
        return detTime;
    }

}
