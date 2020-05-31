package main.java;
import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;

public class buyPanel {
    private double PIX;
    private ArrayList<purchaseItem> purchaseItems;
    private Font objFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 20);
    private Font keyBindFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private Font fundsFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 48);
    private Image background = new Image("res/images/buypanel.png");

    private String keyBinds = "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";

    public Image getBackground() {
        return background;
    }


    public ArrayList<purchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public buyPanel() {
        PIX = 64;
        purchaseItems = new ArrayList<>();
        purchaseItems.add(new purchaseItem<>(new Tank(new Point(PIX,background.getHeight()/2 - 10),
                "res/images/tank.png", 100, 1, 1000, 250)));
        purchaseItems.add(new purchaseItem<>(new superTank(new Point(PIX+120,background.getHeight()/2 - 10),
                "res/images/supertank.png", 150, 3, 500, 600)));
        purchaseItems.add(new purchaseItem<>(new Airplane(new Point(PIX+240, background.getHeight()/2 - 10),
                "res/images/airsupport.png", 500)));
    }

    public void render(int playerFunds) {
        PIX = 64;
        background.drawFromTopLeft(0,0);
        for (purchaseItem purchaseItem: purchaseItems) {
            purchaseItem.getTower().update();
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
