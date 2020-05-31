package main.java;

import bagel.Image;
import bagel.util.Point;

public class Airplane extends Tower {
    private int cost = 500;
    Image projectileImage = new Image("res/images/explosive.png");
    Image tankImage = new Image("res/images/airsupport.png");

    public Airplane(Point point, String imageSrc) {
        super(point, imageSrc);
    }

    public int getCost() {
        return cost;
    }

    public Image getTankImage() {
        return tankImage;
    }

}
