package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class SuperTankProjectile extends TankProjectile {
    private final String IMG_STR = "res/images/supertank_projectile.png";
    private final double SPEED_FACTOR = 3;

    public SuperTankProjectile(Point point) {
        super(point);
        super.setImage(new Image(IMG_STR));
        setDamage(getDamage()*SPEED_FACTOR);
    }

    public SuperTankProjectile copy() {
        SuperTankProjectile copy = new SuperTankProjectile(this.getCenter());
        return copy;
    }

}
