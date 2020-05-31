package main.java;

import bagel.Image;
import bagel.util.Point;

public class superTank extends Tank {
    int effectRadius = 150;
    int damage = 300;
    int cooldown = 500;
    int cost = 600;
    Image projectileImage = new Image("res/images/supertank_projectile.png");
    Image tankImage = new Image("res/images/supertank.png");

    public superTank(Point point, String imageSrc) {
        super(point, imageSrc);
    }


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