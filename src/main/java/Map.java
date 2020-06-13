package main.java;
import bagel.Window;
import bagel.map.*;
import bagel.util.Point;

import java.util.List;

class Map {
    private TiledMap map;
    private List<Point> polylinePoints;

    public Map(int level) {
        map = new TiledMap(String.format("res/levels/%d.tmx", level));
        polylinePoints = map.getAllPolylines().get(0);
    }
    public void render() {
        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());
    }
    public List<Point> getPolylinePoints() {
        return polylinePoints;
    }
    public TiledMap getMap() {
        return map;
    }
}




