package main.java;

import bagel.Image;

public abstract class Tower {
    private int effectRadius;
    private int damage;
    private int cooldown;
    private Image projectileImage;
    private Image tankImage;
    private int cost;

    public abstract Image getTankImage();
    public abstract int getCost();

}
