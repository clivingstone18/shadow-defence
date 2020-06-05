package main.java;
import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;

public class buyPanel extends Panel{
    private double PIX;
    private ArrayList<purchaseItem> purchaseItems;
    private Font objFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 20);
    private Font keyBindFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private Font fundsFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);
    private String keyBinds = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";

    public ArrayList<purchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public buyPanel(Image background, Point point) {
        super(background, point);
        PIX = 64;
        purchaseItems = new ArrayList<>();
        purchaseItems.add(new purchaseItem(new Tank(new Point(PIX,background.getHeight()/2 - 10),
                new Image("res/images/tank.png"))));
        purchaseItems.add(new purchaseItem(new superTank(new Point(PIX+120,background.getHeight()/2 - 10),
                new Image("res/images/supertank.png"))));
        purchaseItems.add(new purchaseItem(new Airplane(new Point(PIX+240, background.getHeight()/2 - 10),
                new Image("res/images/airsupport.png"))));
    }






    //renders the buy panel
    public void render(int playerFunds) {
        PIX = 64;
        super.getBackground().drawFromTopLeft(0,0);
        for (purchaseItem purchaseItem: purchaseItems) {
            purchaseItem.getTower().render();
            purchaseItem.setColour(playerFunds);
            DrawOptions colour = new DrawOptions().setBlendColour(purchaseItem.getColour());
            objFont.drawString("$" + String.valueOf(purchaseItem.getTower().getCost()), PIX-20,
                    super.getBackground().getHeight() - 10, colour);
            PIX += 120;
        }
        keyBindFont.drawString(keyBinds, super.getBackground().getWidth()/2, 20);
        fundsFont.drawString("$" + String.valueOf(playerFunds), Window.getWidth()-200,
                super.getBackground().getHeight() - 30);
    }
}
