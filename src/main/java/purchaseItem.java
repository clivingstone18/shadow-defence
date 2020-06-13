package main.java;
import bagel.util.Colour;

class purchaseItem {
    private Colour colour;
    private Tower tower;

    /**
     *     Sets the colour of the item based on user's funds
     */

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

    public Tower getTower() {
        return this.tower;
    }

    public purchaseItem(Tower tower) {
        this.tower = tower;
    }


}