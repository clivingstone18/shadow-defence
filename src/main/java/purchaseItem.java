package main.java;
import bagel.Image;
import bagel.util.Colour;

class purchaseItem<T extends Tower> {
    private int cost;
    private Colour colour;
    private boolean sufficientFunds;
    private T tower;

    public void setColour(int userFunds) {
        if (userFunds >= cost) {
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