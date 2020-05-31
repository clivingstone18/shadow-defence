package main.java;
import bagel.util.Point;
import static main.java.Map.polylinePoints;

import java.util.ArrayList;
import java.util.List;

public class SuperSlicer extends Slicer implements Spawnable{
    private int targetPointIndex;
    private List<Point> polyline;
    private boolean finished;
    private List<Enemy> children;
    public SuperSlicer(String imgSrc, double speed, int health, int reward, int penalty, Point point) {
        super(imgSrc, speed, health, reward, penalty, point);
        children = new ArrayList<>();

    }

    @Override
    public void SpawnChildren(Point point, int targetPointIndex) {
        Slicer childToSpawn = new Slicer("res/images/slicer.png", 2, 1, 2, 1, point);
        childToSpawn.setTargetPointIndex(targetPointIndex);
        children.add(new Enemy(childToSpawn));
        children.add(new Enemy(childToSpawn));
        for (Enemy e: children) {
            ShadowDefend.activeEnemies.add(e);
        }
    }

}
