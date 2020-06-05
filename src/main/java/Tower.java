package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Tower<T extends Tower> extends Sprite implements Cloneable {
    private int effectRadius;
    private int damage;
    private int cost;
    private double timeElapsed;
    private double FPS = 60;
    private Projectile projectile;

    private boolean activeProjectile;

    public abstract T copy();

    public Tower(Point point, Image imageSrc) {
        super(point, imageSrc);
    }

     public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
     }

     public Projectile getProjectile() {
        return this.projectile;
     }

    public boolean isActiveProjectile() {
        return activeProjectile;
    }

    @Override
    public Tower clone() {
        try {
            return (Tower) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
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

    public void render() {
        super.update();
    }

    public int getEffectRadius() {
        return effectRadius;
    }

    public int getDamage() {return damage;}

    public int getCost() {
        return cost;
    }

    public abstract void detectAndShoot();
    public void update() {
        super.update();
    }

    public abstract void HitTargets();

    public abstract void updateAllProjectiles();

}

