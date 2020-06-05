package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class Tank extends Tower {

    private Slicer target;
    private double cooldown;
    private double timeElapsed;
    private final int FPS = 60;
    private boolean activeProjectile;


    public Tower copy() {
        Tank copy = new Tank(this.getCenter(), this.getImage());
        copy.setCost(this.getCost());
        copy.setDamage(this.getDamage());
        copy.setEffectRadius(this.getEffectRadius());
        copy.setCooldown(this.cooldown);
        copy.target = this.target;
        copy.timeElapsed = this.timeElapsed;
        copy.activeProjectile = this.activeProjectile;
        return copy;
    }


    public Tank(Point point, Image imageSrc) {
        super(point, imageSrc);
        super.setEffectRadius(100);
        super.setDamage(1);
        super.setCost(250);
        super.setProjectile(new TankProjectile(super.getCenter(), "res/images/tank_projectile.png"));
        cooldown = 1000;
        activeProjectile = false;
        timeElapsed = 0;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void detectAndShoot() {
        double shortestDistance = super.getEffectRadius();
        timeElapsed += ShadowDefend.getTimescale() / FPS;
        Slicer proposedTarget = null;
        if (timeElapsed >= cooldown / 1000) {
            timeElapsed = 0;
            for (Slicer enemy : ShadowDefend.activeEnemies) {
                Point enemyLoc = enemy.getCenter();
                double distanceFromTowerToEnemy = enemyLoc.distanceTo(super.getCenter());
                if (distanceFromTowerToEnemy < shortestDistance) {
                    shortestDistance = distanceFromTowerToEnemy;
                    proposedTarget = enemy;
                }
            }
            //choose an enemy
            if (proposedTarget != null) {
                target = proposedTarget;
                Vector2 dirVec = new Vector2(target.getCenter().x - super.getCenter().x,
                        target.getCenter().y - super.getCenter().y);
                this.setAngle(3.14 / 2 + Math.atan2(dirVec.y, dirVec.x));
                super.getProjectile().setDirVec(dirVec);
                super.getProjectile().centerRectAt(super.getCenter());
                activeProjectile = true;
            }
        }
    }

    public void updateAllProjectiles() {
        if (activeProjectile) {
            super.getProjectile().updateProjectile();
        }
    }

    public void HitTargets() {
        if (activeProjectile) {
            if (target != null && target.inBoundingBoxRange(super.getProjectile().getCenter())) {
                target.reduceHealth(getDamage());
                activeProjectile = false;
                target = null;
            }
        }
    }

    public void setActiveProjectile(boolean activeProjectile) {
        this.activeProjectile = activeProjectile;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

}
