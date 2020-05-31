package main.java;
import bagel.util.Point;
import static main.java.Map.polylinePoints;
import java.util.List;

public class SuperSlicer extends Slicer implements Spawnable{
    private int targetPointIndex;
    private List<Point> polyline;
    private boolean finished;

    public SuperSlicer(String imgSrc, double speed, int health, int reward, int penalty) {
        super(imgSrc, speed, health, reward, penalty);
    }
}
