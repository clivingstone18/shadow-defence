package main.java;

import bagel.Image;
import bagel.util.Point;

public abstract class Tower<T extends Tower> extends Sprite {
    private int effectRadius;
    private int damage;
    private int cost;
    private Projectile projectile;

    public abstract T copy();

    public Tower(Point point, Image imageSrc) {
        super(point, imageSrc);
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
     }

    public void update() {
        super.update();
    }

    public Projectile getProjectile() {
        return this.projectile;
     }

    public void setEffectRadius(int effectRadius) {
        this.effectRadius = effectRadius;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getEffectRadius() {
        return effectRadius;
    }

    public int getDamage() {return damage;}

    public int getCost() {
        return cost;
    }

    public abstract void detectAndShoot();

    public abstract void HitTargets();

    public abstract void updateAllProjectiles();

}

