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

    // Creates a deep copy of the tank
    public Tank copy() {
        Tank copy = new Tank(this.getCenter(), this.getImage());
        copy.setCost(this.getCost());
        copy.setDamage(this.getDamage());
        copy.setEffectRadius(this.getEffectRadius());
        copy.setCooldown(this.cooldown);
        copy.target = this.target;
        copy.timeElapsed = this.timeElapsed;
        copy.activeProjectile = this.activeProjectile;
        copy.setProjectile(getProjectile().copy());
        return copy;
    }

    public Tank(Point point, Image imageSrc) {
        super(point, imageSrc);
        super.setEffectRadius(100);
        super.setDamage(1);
        super.setCost(250);
        super.setProjectile(new TankProjectile(super.getCenter()));
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
        // If the cooldown period has passed, the tower can select a new target (if there is one in range)
        if (timeElapsed >= cooldown / 1000) {
            timeElapsed = 0;
            for (Slicer enemy : ShadowDefend.activeEnemies) {
                Point enemyLoc = enemy.getCenter();
                double distanceFromTowerToEnemy = enemyLoc.distanceTo(super.getCenter());
                // Selection of the closest enemy
                if (distanceFromTowerToEnemy < shortestDistance) {
                    shortestDistance = distanceFromTowerToEnemy;
                    proposedTarget = enemy;
                }
            }
            // Assigning the target (the closest enemy)
            if (proposedTarget != null) {
                target = proposedTarget;
                // Sets the direction of the tank
                Vector2 dirVec = new Vector2(target.getCenter().x - super.getCenter().x,
                        target.getCenter().y - super.getCenter().y);
                this.setAngle(Math.PI/2 + Math.atan2(dirVec.y, dirVec.x));
                super.getProjectile().setDirVec(dirVec);
                super.getProjectile().centerRectAt(super.getCenter());
                // Marks that tank is in action
                activeProjectile = true;
            }
        }
    }

    // Updates the position projectile(s) currently aimed at a target
    public void updateAllProjectiles() {
        if (activeProjectile) {
            super.getProjectile().updateProjectile();
        }
    }

    // Projectiles do damage to target in the range
    public void HitTargets() {
        if (activeProjectile) {
            if (target != null && target.inBoundingBoxRange(super.getProjectile().getCenter())) {
                // Affects the target
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
