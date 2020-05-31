package main.java;

import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

import java.util.List;

public class Attacker<T extends Tower> {
    private Point position;
    private T tower;
    private double effectRadius;
    public Rectangle effectRect;
    public Enemy target;
    private double timeElapsed;
    private double FPS = 60;
    private Projectile projectile;

    public Projectile getProjectile() {
        return projectile;
    }

    public Attacker(Point position, T tower) {
        this.position = position;
        this.tower = tower;
        this.effectRadius = tower.getEffectRadius();
        this.effectRect = new Rectangle(new Point(position.x - effectRadius, position.y - effectRadius), effectRadius,
                effectRadius);
        this.timeElapsed = 0;
    }

    public Tower getTower() {
        return tower;
    }

    public void deleteProjectile() {
        projectile = null;
    }

    public Point getPosition() {
        return position;
    }

    public void detectAndShoot(List<Enemy> activeEnemies) {
        double shortestDistance = effectRadius;
        Enemy proposedTarget = null;
        timeElapsed += ShadowDefend.getTimescale() / FPS;
        if (timeElapsed >= tower.getCooldown() / 1000) {
            timeElapsed = 0;
            for (Enemy enemy : activeEnemies) {
                Point enemyLoc = enemy.getEnemyType().getCenter();
                double distanceFromTowerToEnemy = enemyLoc.distanceTo(position);
                if (distanceFromTowerToEnemy < shortestDistance) {
                    shortestDistance = distanceFromTowerToEnemy;
                    proposedTarget = enemy;
                }
            }
            //choose an enemy
            if (proposedTarget != null) {
                target = proposedTarget;
                Vector2 dirVec = new Vector2(target.getEnemyType().getCenter().x - position.x,
                        target.getEnemyType().getCenter().y - position.y);
                tower.setAngle(3.14 / 2 + Math.atan2(dirVec.y, dirVec.x));
                projectile = new TankProjectile(position, dirVec);
            }
        }
    }

    public boolean hasHitTarget() {
        if (Sprite.cursorInRange(projectile.getCenter(), target.getEnemyType().getRect())) {
            System.out.println(tower.getDamage());
            System.out.println(target.getHealth());
            target.reduceHealth(tower.getDamage());
            return true;
        }
        else {
            return false;
        }

    }
}




