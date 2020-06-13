package main.java;

import bagel.util.Point;
import bagel.util.Vector2;

public abstract class Projectile extends Sprite {
    private double damage;
    private Vector2 dirVec;

    public void setDirVec(Vector2 dirVec) {
        this.dirVec = dirVec;
    }
    public Vector2 getDirVec() {return dirVec;}
    public double getDamage() {
        return damage;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public Projectile(Point point) {
        super(point);
    }
    public abstract void updateProjectile();
    public abstract Projectile copy();
}


