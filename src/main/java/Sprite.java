/* Obtained from project 1 solution package with modifications */

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
        this.position = point;
    }
    public Sprite(Point point) {
        this.angle = 0;
        this.position = point;
    }
    public void setImage(Image image) {
        this.image = image;
        this.rect = image.getBoundingBoxAt(position);
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

    //Checks if it doesn't overlap with images of the status and buy panel
    public boolean validRenderingPoint(Point point) {
        Panel buyPanel = new buyPanel(ShadowDefend.buyPanelBackground, ShadowDefend.buyPanelCenter);
        Panel statusPanel = new statusPanel(ShadowDefend.statusPanelBackground, ShadowDefend.statusPanelCenter);
        return !statusPanel.inBoundingBoxRange(point) && !buyPanel.inBoundingBoxRange(point);
    }

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