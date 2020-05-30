package main.java;
import bagel.util.Point;
import static main.java.Map.polylinePoints;
import java.util.List;

public class SuperSlicer extends Slicer implements Spawnable{
    private double speed = 0.75 * super.getSpeed();
    private int health = super.getHealth();
    private int reward = 15;
    private int penalty = super.getPenalty() * 2;
    private int targetPointIndex;
    private List<Point> polyline;
    private boolean finished;

    public SuperSlicer(String imgSrc) {
        super(imgSrc);
    }
}
