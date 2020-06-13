package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class TankProjectile extends Projectile {
    private final double VELOCITY = 10;
    private final double DAMAGE = 1;

    public TankProjectile(Point point) {
        super(point);
        super.setDamage(DAMAGE);
        super.setImage(new Image("res/images/tank_projectile.png"));
    }

    public Projectile copy() {
        Projectile copy = new TankProjectile(this.getCenter());
        return copy;
    }

    public void updateProjectile() {
        super.move(super.getDirVec().normalised().mul(VELOCITY * ShadowDefend.getTimescale()));
        super.update();
    }

}
