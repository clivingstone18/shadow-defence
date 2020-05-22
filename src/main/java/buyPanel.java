import bagel.Image;
import bagel.Window;

import java.util.ArrayList;
import java.util.List;

public class buyPanel {
    int PIX = 64;
    Image background = new Image("res/images/buypanel.png");
    ArrayList<purchaseItem> purchaseItems;

    public buyPanel() {
        purchaseItems = new ArrayList<>();
        purchaseItems.add(new purchaseItem<Tank>(new Tank()));
        purchaseItems.add(new purchaseItem<superTank>(new superTank()));
        purchaseItems.add(new purchaseItem<Airplane>(new Airplane()));
    }

    public void render() {
        background.draw(Window.getWidth()/2,0);
        for (purchaseItem purchaseItem: purchaseItems) {
            purchaseItem.getTower().getProjectileImage().draw(PIX, 10);
            PIX += 120;
        }
    }


}
