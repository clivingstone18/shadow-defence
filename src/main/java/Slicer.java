package main.java;

import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

import static main.java.Map.polylinePoints;

public class Slicer extends Sprite {
    private double speed;
    private int health;
    private int reward;
    private int penalty;
    private int targetPointIndex;
    private List<Point> polyline;
    private boolean finished;
    private Vector2 position;

    public int getTargetPointIndex() {
        return targetPointIndex;
    }

    public void setTargetPointIndex(int targetPointIndex) {
        this.targetPointIndex = targetPointIndex;
    }

    public double getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getReward() {
        return reward;
    }

    public Slicer(String imgSrc, double speed, int health, int reward, int penalty, Point point){
        super(point, imgSrc);
        this.polyline = polylinePoints;
        this.targetPointIndex = 1;
        this.finished = false;
        this.speed=speed;
        this.health=health;
        this.reward=reward;
        this.penalty=penalty;
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

}
