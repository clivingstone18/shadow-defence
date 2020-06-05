package main.java;
import bagel.util.Point;
import bagel.util.Vector2;

public class SuperTankProjectile extends TankProjectile {
    public SuperTankProjectile(Point point, String imgStr) {
        super(point, imgStr);
        setDamage(getDamage()*3);
    }

}
