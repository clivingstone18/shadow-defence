package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class SuperTankProjectile extends TankProjectile {
    public SuperTankProjectile(Point point) {
        super(point);
        super.setImage(new Image("res/images/supertank_projectile.png"));
        setDamage(getDamage()*3);
    }

    public Projectile copy() {
        Projectile copy = new SuperTankProjectile(this.getCenter());
        return copy;
    }

}
