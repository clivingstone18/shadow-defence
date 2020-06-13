package main.java;
import bagel.Image;
import bagel.util.Point;

public class superTank extends Tank {
    public superTank(Point point, Image imageSrc) {
        super(point, imageSrc);
        super.setCost(600);
        super.setEffectRadius(150);
        super.setDamage(super.getDamage() * 3);
        super.setCooldown(500);
        super.setProjectile(new SuperTankProjectile(super.getCenter()));
        super.setActiveProjectile(false);
    }

}