package main.java;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.List;

class Map {
    private TiledMap map = new TiledMap("res/levels/1.tmx");
    private List<Point> polylinePoints = map.getAllPolylines().get(0);

    public void render() {
        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());
    }

    public List<Point> getPolylinePoints() {
        return polylinePoints;
    }
}




