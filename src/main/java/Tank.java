package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Tank extends Tower {
    private int effectRadius;
    private int damage;
    private double cooldown;
    private int cost;
    Image projectileImage = new Image("res/images/tank_projectile.png");
    Image tankImage = new Image("res/images/tank.png");

    public Tank(Point point, String imageSrc, int effectRadius, int damage, double cooldown, int cost) {
        super(point, imageSrc);
        this.effectRadius = effectRadius;
        this.damage = damage;
        this.cooldown = cooldown;
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getEffectRadius() {
        return effectRadius;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public double getCooldown() {
        return cooldown;
    }

}