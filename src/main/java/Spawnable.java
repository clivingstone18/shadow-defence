package main.java;

import bagel.util.Point;

import java.util.Collection;
import java.util.List;

public interface Spawnable {
    List<Slicer> getChildrenToSpawn();
}


