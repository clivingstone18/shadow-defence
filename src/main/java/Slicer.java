/* Adapted from project 1 solution package */

package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

public class Slicer extends Sprite implements Attackable, Cloneable {
    private double speed;
    private int health;

    public int getReward() {
        return reward;
    }

    private int reward;
    private int penalty;
    private int targetPointIndex;
    private List<Point> polyline;
    private boolean finished;

    public int getTargetPointIndex() {
        return targetPointIndex;
    }

    public void setTargetPointIndex(int targetPointIndex) {
        this.targetPointIndex = targetPointIndex;
    }

    public Slicer(Point point, List<Point> polylinePoints) {
        super(point);
        super.setImage(new Image("res/images/slicer.png"));
        this.polyline = polylinePoints;
        this.targetPointIndex = 1;
        this.finished = false;
        this.speed = 2;
        this.health = 1;
        this.reward = 2;
        this.penalty = 1;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public void update() {
        if (finished) {
            return;
        }
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < speed * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Make our focus the next point in the polyline
                targetPointIndex += 1;
            }
        }

        super.move(distance.normalised().mul(speed * ShadowDefend.getTimescale()));
        super.setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update();
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isEliminated() {
        if (health <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setIsFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean getIsFinished() {
        return finished;
    }

    // Creates a copy of the slicer
    public Slicer copy() {
        Slicer copy = new Slicer(getCenter(), polyline);
        copy.setTargetPointIndex(this.getTargetPointIndex());
        copy.setIsFinished(this.getIsFinished());
        return copy;
    }

    public void reduceHealth(int towerDamage) {
        health -= towerDamage;
    }
}