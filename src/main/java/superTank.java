package main.java;
import bagel.Image;
import bagel.util.Point;

public class superTank extends Tank {

    private final int SUPERTANK_EFFECT_RADIUS = 150;
    private final int DAMAGE_FACTOR = 3;
    private final int SUPERTANK_COST = 600;
    private final double SUPERTANK_COOLDOWN = 500;

    public superTank(Point point, Image imageSrc) {
        super(point, imageSrc);
        super.setCost(SUPERTANK_COST);
        super.setEffectRadius(SUPERTANK_EFFECT_RADIUS);
        super.setDamage(super.getDamage() * DAMAGE_FACTOR);
        super.setCooldown(SUPERTANK_COOLDOWN);
        super.setProjectile(new SuperTankProjectile(super.getCenter()));
        super.setActiveProjectile(false);
    }

}