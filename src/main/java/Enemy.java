package main.java;

import bagel.util.Point;

public class Enemy<E extends Slicer> {
    private E enemyType;
    private double speed;
    private int health;
    private int reward;
    private int penalty;
    private Point position;
    public Enemy(E enemy) {
        this.enemyType = enemy;
        this.speed = enemyType.getSpeed();
        this.health = enemyType.getHealth();
        this.reward = enemyType.getReward();
        this.penalty = enemyType.getPenalty();
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getHealth() {
        return this.health;
    }

    public int getReward() {
        return this.reward;
    }

    public int getPenalty() {
        return this.penalty;
    }

    public Point getPosition() {
        return this.position;
    }

    public void reduceHealth(int damage) {
        this.health -= damage;
    }

    public boolean isEliminated() {
        if (health <= 0) {
            ShadowDefend.playerFunds += reward;
            return true;
        }
        else {
            return false;
        }
    }

    public E getEnemyType() {
        return this.enemyType;
    }
}
