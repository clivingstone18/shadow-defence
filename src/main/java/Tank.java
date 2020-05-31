package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Tank extends Tower {
    int effectRadius = 100;
    int damage = 1;
    int cooldown = 1000;
    int cost = 250;
    Image projectileImage = new Image("res/images/tank_projectile.png");
    Image tankImage = new Image("res/images/tank.png");

    public Tank(Point point, String imageSrc) {
        super(point, imageSrc);
    }

    public int getEffectRadius() {
        return effectRadius;
    }

    public int getDamage() {
        return damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public Image getProjectileImage() {
        return projectileImage;
    }

    public Image getTankImage() {
        return tankImage;
    }

    public int getCost() {
        return cost;
    }


}