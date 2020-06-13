package main.java;

import bagel.Image;
import bagel.util.Point;

public class TankProjectile extends Projectile {
    private final double VELOCITY = 10;
    private final double DAMAGE = 1;

    public TankProjectile(Point point) {
        super(point);
        super.setDamage(DAMAGE);
        super.setImage(new Image("res/images/tank_projectile.png"));
    }

    /**
     * Creates a deep clone of the tank projectile
     * @return a deep clone of the tank projectile
     */
    public TankProjectile copy() {
        TankProjectile copy = new TankProjectile(this.getCenter());
        return copy;
    }

    /**
     * Updates the movement of the projectile
     */

    public void updateProjectile() {
        super.move(super.getDirVec().normalised().mul(VELOCITY * ShadowDefend.getTimescale()));
        super.update();
    }

}
