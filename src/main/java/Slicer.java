package main.java;

import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;

import static main.java.Map.polylinePoints;

public class Slicer extends Sprite {
    private double speed = 1;
    private int health = 2;
    private int reward = 1;
    private int penalty = 2;
    private int targetPointIndex;
    private List<Point> polyline;
    private boolean finished;

    public double getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getPenalty() {
        return penalty;
    }

    public Slicer(String imgSrc) {
        super(polylinePoints.get(0), imgSrc);
        this.polyline = polylinePoints;
        this.targetPointIndex = 1;
        this.finished = false;
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
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update();
    }

    public boolean isFinished() {
        return finished;
    }

}
