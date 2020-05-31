package main.java;

import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class ApexSlicer extends MegaSlicer {

    private List<Enemy> children;

    public ApexSlicer(String imgSrc, double speed, int health, int reward, int penalty, Point point) {
        super(imgSrc, speed, health, reward, penalty, point);
        children = new ArrayList<>();

    }
    public void SpawnChildren(Point point, int targetPointIndex) {
        MegaSlicer childToSpawn = new MegaSlicer("res/images/megaslicer.png", 0.75*2, 2, 10,  4, point);
        childToSpawn.setTargetPointIndex(targetPointIndex);
        children.add(new Enemy(childToSpawn));
        children.add(new Enemy(childToSpawn));
        children.add(new Enemy(childToSpawn));
        children.add(new Enemy(childToSpawn));
        for (Enemy e: children) {
            ShadowDefend.activeEnemies.add(e);
        }
    }
}
