package main.java;
import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;

public class buyPanel extends Panel {
    private final double PIXFACTOR = 64;
    private final double OFFSET = 10;
    private final double ITEMDIST = 120;
    private final double MONEYOFFSETX = 200;
    private final double MONEYOFFSETY = 65;
    private final double FONTOFFSET = 20;
    private final String DOLLAR = "$";
    private double pix;
    private final Image panelBackground;
    private final double MIDPOINT;
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
        panelBackground = background;
        MIDPOINT = background.getHeight()/2;
        purchaseItems = new ArrayList<>();
        purchaseItems.add(new purchaseItem(new Tank(new Point(PIXFACTOR,MIDPOINT - OFFSET),
                new Image("res/images/tank.png"))));
        purchaseItems.add(new purchaseItem(new superTank(new Point(PIXFACTOR+ITEMDIST,MIDPOINT - OFFSET),
                new Image("res/images/supertank.png"))));
        purchaseItems.add(new purchaseItem(new Airplane(new Point(PIXFACTOR+ITEMDIST+ITEMDIST, MIDPOINT - OFFSET),
                new Image("res/images/airsupport.png"))));
    }

    /**
     * Renders the buy panel
     * @param playerFunds
     */
    public void render(int playerFunds) {
        pix = PIXFACTOR;
        super.getBackground().drawFromTopLeft(0,0);
        for (purchaseItem purchaseItem: purchaseItems) {
            Point point = purchaseItem.getTower().getCenter();
            purchaseItem.getTower().getImage().draw(point.x, point.y);
            purchaseItem.setColour(playerFunds);
            DrawOptions colour = new DrawOptions().setBlendColour(purchaseItem.getColour());
            objFont.drawString(DOLLAR + String.valueOf(purchaseItem.getTower().getCost()),
                    pix-FONTOFFSET,
                    super.getBackground().getHeight() - OFFSET, colour);
            pix += ITEMDIST;
        }
        keyBindFont.drawString(keyBinds, super.getBackground().getWidth()/2, FONTOFFSET);
        fundsFont.drawString(DOLLAR + String.valueOf(playerFunds), Window.getWidth()-MONEYOFFSETX,
                MONEYOFFSETY);
    }
 
}
