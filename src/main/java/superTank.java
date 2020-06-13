package main.java;
import bagel.Image;
import bagel.util.Point;

public class superTank extends Tank {

    private final int TANK_EFFECT_RADIUS = 150;
    private final int DAMAGE_FACTOR = 3;
    private final int COST = 600;
    private final double COOLDOWN = 1000;

    public superTank(Point point, Image imageSrc) {
        super(point, imageSrc);
        super.setCost(COST);
        super.setEffectRadius(TANK_EFFECT_RADIUS);
        super.setDamage(super.getDamage() * DAMAGE_FACTOR);
        super.setCooldown(COOLDOWN);
        super.setProjectile(new SuperTankProjectile(super.getCenter()));
        super.setActiveProjectile(false);
    }

}