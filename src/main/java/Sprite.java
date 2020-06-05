/* Obtained from project 1 solution package */

package main.java;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Represents a game entity
 */
public abstract class Sprite {

    private Image image;
    private Rectangle rect;
    private double angle;
    private Point position;

    public Sprite(Point point, Image imageSrc) {
        this.image = imageSrc;
        this.rect = image.getBoundingBoxAt(point);
        this.angle = 0;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRect() {
        return new Rectangle(rect);
    }
    public void setRect(Rectangle rectangle) {
        rect = rectangle;
    }


    public void centerRectAt(Point point) {
        this.rect = image.getBoundingBoxAt(point);
    }

    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    public Point getCenter() {
        return rect.centre();
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean inBoundingBoxRange(Point point) {
        boolean validX = (point.x >= rect.left()) && (point.x <= rect.right());
        boolean validY = (point.y >= rect.top()) && (point.y <= rect.bottom());
        //check if clicked within bounding box
        return validX && validY;
    }

    public boolean outOfBounds() {
        boolean outOfWindow = ((this.getCenter().x > Window.getWidth()-1)  || (this.getCenter().y -1 > Window.getHeight()));
        return outOfWindow;
    }


    public boolean validRenderingPoint(Point point) {
        return !ShadowDefend.buyPanel.inBoundingBoxRange(point) && !ShadowDefend.statusPanel.inBoundingBoxRange(point);
    }

    /**
     * Updates the Sprite. Default behaviour is to render the Sprite at its current position.
     */

    public void update() {
        if (validRenderingPoint(getCenter())) {
            image.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(angle));
        }
    }
    public void update(Point point) {
        if (validRenderingPoint(point)) {
            image.draw(point.x, point.y, new DrawOptions().setRotation(angle));
        }
    }


}