package main.java;

import bagel.Image;

public class superTank extends Tank {
    int effectRadius = 150;
    int damage = super.getEffectRadius()*3;
    int cooldown = 500;
    int cost = 600;
    Image projectileImage = new Image("res/images/supertank_projectile.png");
    Image tankImage = new Image("res/images/supertank.png");

    public int getCost() {
        return cost;
    }
    public Image getProjectileImage() {
        return projectileImage;
    }
    public Image getTankImage() {
        return tankImage;
    }
}