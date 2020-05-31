package main.java;

import bagel.Image;
import bagel.util.Point;

public abstract class Tower extends Sprite implements Cloneable {

    public Tower(Point point, String imageSrc) {
        super(point, imageSrc);
    }
    public static Tower createNewTower(Tower tower) {
        return tower;
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

    public abstract int getCost();


}
