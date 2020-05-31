package main.java;

import bagel.Image;
import bagel.util.Point;

public class superTank extends Tank {
    Image projectileImage = new Image("res/images/supertank_projectile.png");
    Image tankImage = new Image("res/images/supertank.png");

    public superTank(Point point, String imageSrc, int effectRadius,  int damage, int cooldown, int cost) {
        super(point, imageSrc, effectRadius, damage, cooldown, cost);
    }

    public Image getProjectileImage() {
        return projectileImage;
    }
    public Image getTankImage() {
        return tankImage;
    }

}