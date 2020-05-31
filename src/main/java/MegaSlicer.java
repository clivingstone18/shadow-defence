package main.java;

import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MegaSlicer extends SuperSlicer {
    private List<Enemy> children;
    public MegaSlicer(String imgSrc, double speed, int health, int reward, int penalty, Point point) {
        super(imgSrc, speed, health, reward, penalty, point);
        children = new ArrayList<>();

    }

    @Override
    public void SpawnChildren(Point point, int targetPointIndex) {
        SuperSlicer childToSpawn = new SuperSlicer("res/images/superslicer.png", 0.75, 1, 15,  2, point);
        childToSpawn.setTargetPointIndex(targetPointIndex);
        children.add(new Enemy(childToSpawn));
        children.add(new Enemy(childToSpawn));
        for (Enemy e: children) {
            ShadowDefend.activeEnemies.add(e);
        }
    }
}
