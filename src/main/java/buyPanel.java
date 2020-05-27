package main.java;
import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;

import java.util.ArrayList;

public class buyPanel {
    private double PIX;
    private Image background = new Image("res/images/buypanel.png");
    private ArrayList<purchaseItem> purchaseItems;
    private int playerFunds;
    private Font objFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 20);
    private Font keyBindFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private Font fundsFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);

    private String keyBinds = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";


    public Image getBackground() {
        return background;
    }

    public ArrayList<purchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public buyPanel() {
        purchaseItems = new ArrayList<>();
        purchaseItems.add(new purchaseItem<Tank>(new Tank()));
        purchaseItems.add(new purchaseItem<superTank>(new superTank()));
        purchaseItems.add(new purchaseItem<Airplane>(new Airplane()));
    }

    public void updateBuyPanel(int funds) {
        playerFunds = funds;
    }

    public void render() {
        PIX = 64;
        background.drawFromTopLeft(0, 0);
        for (purchaseItem purchaseItem: purchaseItems) {
            purchaseItem.getTower().getTankImage().draw(PIX, background.getHeight()/2 - 10);
            purchaseItem.setColour(playerFunds);
            DrawOptions colour = new DrawOptions().setBlendColour(purchaseItem.getColour());
            objFont.drawString("$" + String.valueOf(purchaseItem.getTower().getCost()), PIX-20,
                    background.getHeight() - 10, colour);
            PIX += 120;
        }
        keyBindFont.drawString(keyBinds, background.getWidth()/2, 20);
        fundsFont.drawString("$" + String.valueOf(playerFunds), Window.getWidth()-200, background.getHeight() - 30);
    }


}
