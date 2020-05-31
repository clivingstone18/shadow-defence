package main.java;

import bagel.util.Point;
import bagel.util.Vector2;

public class TankProjectile extends Projectile {
    private Vector2 dirVec;

    public TankProjectile(Point point, Vector2 dirVec) {
        super(point, "res/images/tank_projectile.png", 10, 1);
        super.update(point);
        this.dirVec = dirVec;
    }

    public void update() {
        super.move(dirVec.normalised().mul(10 * ShadowDefend.getTimescale()));
        super.update();
    }



}
