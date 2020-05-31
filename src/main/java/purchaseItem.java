package main.java;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Rectangle;

class purchaseItem<T extends Tower> {
    private Colour colour;
    private boolean sufficientFunds;
    private T tower;

    public void setColour(int userFunds) {
        if (userFunds >= tower.getCost()) {
            //makes it green if funds are sufficient
            this.colour = new Colour(0, 255, 0);
        } else {
            //otherwise makes it red
            this.colour = new Colour(255, 0, 0);
        }
    }

    public Colour getColour() {
        return colour;
    }

    public T getTower() {
        return this.tower;
    }

    public purchaseItem(T tower) {
        this.tower = tower;
    }


}