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

    /**
     * Returns whether a point is in the bounding box of the sprite
     * @param point
     * @return whether a point is in the bounding box of the sprite
     */

    public boolean inBoundingBoxRange(Point point) {
        boolean validX = (point.x >= rect.left()) && (point.x <= rect.right());
        boolean validY = (point.y >= rect.top()) && (point.y <= rect.bottom());
        //check if clicked within bounding box
        return validX && validY;
    }

    /**
     * Returns whether the Sprite is out of bounds
     * @return whether the Sprite is out of bounds
     */
    public boolean outOfBounds() {
        boolean outOfWindow = ((this.getCenter().x > Window.getWidth()-1)  || (this.getCenter().y -1 > Window.getHeight()));
        return outOfWindow;
    }

    /**
     * Checks whether the sprite intersects with the buy/status panel (rendering it an invalid position)
     * @param point
     * @return whether the sprite intersects with the buy/status panel
     */
    public boolean validRenderingPoint(Point point) {
        Panel buyPanel = new buyPanel(ShadowDefend.buyPanelBackground, ShadowDefend.buyPanelCenter);
        Panel statusPanel = new statusPanel(ShadowDefend.statusPanelBackground, ShadowDefend.statusPanelCenter);
        return !statusPanel.inBoundingBoxRange(point) && !buyPanel.inBoundingBoxRange(point);
    }

    /** Updates the position of the sprite
     * Default behaviour is to update it at its current position, otherwise at point provided
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