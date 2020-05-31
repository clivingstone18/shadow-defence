package main.java;

import bagel.Image;
import bagel.util.Point;

public class Airplane extends Tower {
    private int cost = 500;
    Image projectileImage = new Image("res/images/explosive.png");
    Image tankImage = new Image("res/images/airsupport.png");

    public Airplane(Point point, String imageSrc, int cost) {
        super(point, imageSrc);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public double getCooldown() {
        return 0;
    }

    public Image getTankImage() {
        return tankImage;
    }

}
