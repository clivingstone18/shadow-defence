package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Panel {
    private Rectangle rect;
    private Image background;
    private Point position;

    public Panel(Image background, Point position) {
        this.background = background;
        this.position = position;
        this.rect = background.getBoundingBoxAt(position);
    }

    public Image getBackground() {
        return background;
    }

    public Point getPosition() {
        return position;
    }

    public boolean inBoundingBoxRange(Point point) {
        boolean validX = (point.x >= rect.left()) && (point.x <= rect.right());
        boolean validY = (point.y >= rect.top()) && (point.y <= rect.bottom());
        //check if clicked within bounding box
        return validX && validY;
    }

}
