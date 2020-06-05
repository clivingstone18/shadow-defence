package main.java;

import bagel.util.Point;
import bagel.util.Vector2;

public class TankProjectile extends Projectile {
    private double velocity;

    public TankProjectile(Point point, String imgStr) {
        super(point, imgStr,1);
        this.velocity = 10;
    }

    public void updateProjectile() {
        super.move(super.getDirVec().normalised().mul(velocity * ShadowDefend.getTimescale()));
        super.update();
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
