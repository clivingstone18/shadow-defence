package main.java;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

public class Tank extends Tower {
    private double cooldown;
    private double timeElapsed;
    private final int FPS = 60;
    private boolean activeProjectile;
    private Slicer currTarget;

    private final int TANK_EFFECT_RADIUS = 100;
    private final int TANK_DAMAGE = 1;
    private final int TANK_COST = 250;
    private final double TANK_COOLDOWN = 1000;

    // Setter Methods

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setActiveProjectile(boolean activeProjectile) {
        this.activeProjectile = activeProjectile;
    }
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }



    /**
     * Creates a deep clone of the tank
     * @return a deep clone of the tank
     */

    public Tank copy() {
        Tank copy = new Tank(this.getCenter(), this.getImage());
        copy.setCost(this.getCost());
        copy.setDamage(this.getDamage());
        copy.setEffectRadius(this.getEffectRadius());
        copy.setCooldown(this.cooldown);
        copy.timeElapsed = this.timeElapsed;
        copy.activeProjectile = this.activeProjectile;
        copy.setProjectile(getProjectile().copy());
        return copy;
    }

    public Tank(Point point, Image imageSrc) {
        super(point, imageSrc);
        super.setEffectRadius(TANK_EFFECT_RADIUS);
        super.setDamage(TANK_DAMAGE);
        super.setCost(TANK_COST);
        super.setProjectile(new TankProjectile(super.getCenter()));
        cooldown = TANK_COOLDOWN;
        activeProjectile = false;
        timeElapsed = 0;
        currTarget = null;
    }

    /**
     * Identifies a feasible target (within effect radius)
     * Selects the closest one
     * @param activeEnemies
     */
    public void identifyTarget(List<Slicer> activeEnemies) {
        double shortestDistance = super.getEffectRadius();
        timeElapsed += ShadowDefend.getTimescale() / FPS;
        Slicer proposedTarget = null;
        // If the cooldown period has passed, the tower can select a new target (if there is one in range)
        if (timeElapsed >= cooldown / 1000) {
            timeElapsed = 0;
            for (Slicer enemy : activeEnemies) {
                Point enemyLoc = enemy.getCenter();
                double distanceFromTowerToEnemy = enemyLoc.distanceTo(super.getCenter());
                // Selection of the closest enemy
                if (distanceFromTowerToEnemy < shortestDistance) {
                    shortestDistance = distanceFromTowerToEnemy;
                    proposedTarget = enemy;
                }
            }
            if (proposedTarget != null) {
                // Sets the direction of the tank
                currTarget = proposedTarget;
                Vector2 dirVec = new Vector2(currTarget.getCenter().x - super.getCenter().x,
                        currTarget.getCenter().y - super.getCenter().y);
                this.setAngle(Math.PI / 2 + Math.atan2(dirVec.y, dirVec.x));
                super.getProjectile().setDirVec(dirVec);
                super.getProjectile().centerRectAt(super.getCenter());
                // Marks that tank is in action
                activeProjectile = true;
            }
        }
    }

    /**
     *  Updates the position of the projectile(s) currently aimed at a target
     */
    public void updateAllProjectiles(){
        if (activeProjectile) {
            super.getProjectile().updateProjectile();
        }
    }

    /**
     * / Projectiles do damage to target in the range
     */
    public void HitTarget() {
        if (activeProjectile) {
            if (currTarget.inBoundingBoxRange(super.getProjectile().getCenter())) {
                // Affects the target
                currTarget.reduceHealth(getDamage());
                activeProjectile = false;
                // New target
                currTarget = null;
            }
        }
    }

}
