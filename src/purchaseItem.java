import bagel.Image;
import Colour.java;

class purchaseItem extends Image {
    private int cost;
    private Colour colour;
    private boolean sufficientFunds;
    private <T extends Tower> tower;

    public checkFunds(int userFunds) {
        if (userFunds >= cost) {
            //makes it green if funds are sufficient
            this.colour = new Colour(0, 255, 0);
        } else {
            //otherwise makes it red
            thos.colour = new Colour(255, 0, 0);
        }
    }

    public setItem(T tower) {
        this.tower = new T();
    }

    public drawItem() {
        super(this.tower.gettankImage());

    }

}