package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public abstract class Projectile extends Sprite {
    private double damage;
    private Image projectileImage;

    private Vector2 dirVec;

    public Projectile(Point point) {
        super(point);
    }

    public abstract Projectile copy();

    public void setDirVec(Vector2 dirVec) {
        this.dirVec = dirVec;
    }
    public Vector2 getDirVec() {return dirVec;}

    public void update() {
        super.update();
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public abstract void updateProjectile();
}

