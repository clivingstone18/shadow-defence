package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class TankProjectile extends Projectile {
    private double velocity;

    public TankProjectile(Point point) {
        super(point,1);
        super.setImage(new Image("res/images/tank_projectile.png"));
        this.velocity = 10;
    }

    public Projectile copy() {
        Projectile copy = new TankProjectile(this.getCenter());
        return copy;
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
