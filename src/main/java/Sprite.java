package main.java;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Represents a game entity
 */
public abstract class Sprite {

    private final Image image;
    private final Rectangle rect;
    private double angle;

    /**
     * Creates a new Sprite (game entity)
     *
     * @param point    The starting point for the entity
     * @param imageSrc The image which will be rendered at the entity's point
     */
    public Sprite(Point point, String imageSrc) {
        this.image = new Image(imageSrc);
        this.rect = image.getBoundingBoxAt(point);
        this.angle = 0;
    }

    public Image getImage() {
        return image;
    }


    public Rectangle getRect() {
        return new Rectangle(rect);
    }

    /**
     * Moves the Sprite by a specified delta
     *
     * @param dx The move delta vector
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    public Point getCenter() {
        return getRect().centre();
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public static boolean cursorInRange(Point point, Rectangle rect) {
        boolean validX = (point.x >= rect.left()) && (point.x <= rect.right());
        boolean validY = (point.y >= rect.top()) && (point.y <= rect.bottom());
        //check if cliced within bounding box
        return validX && validY;
    }

    /**
     * Updates the Sprite. Default behaviour is to render the Sprite at its current position.
     */
    public void update() {
        image.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(angle));
    }
    public void update(Point point) {
        image.draw(point.x, point.y, new DrawOptions().setRotation(angle));
    }


}