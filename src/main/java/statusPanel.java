package main.java;

import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;

public class statusPanel extends Panel {
    private int PANEL_FONT_SIZE = 14;
    private final double FONT_OFFSET = 20;
    private final double ITEM_OFFSET = 230;
    private final double TIMESCALELIM = 1;
    private final int MAX_COLOUR = 255;
    private Font objFont = new Font("res/fonts/DejaVuSans-Bold.ttf", PANEL_FONT_SIZE);
    private int pix;
    private DrawOptions colour;

    public statusPanel(Image backgroundImg, Point point) {
        super(backgroundImg, point);
    }

    /**
     * Renders the status panel
     * @param waveNo
     * @param status
     * @param lives
     */
    public void render(int waveNo, String status, int lives) {
        pix = 0;
        super.getBackground().draw(super.getPosition().x, super.getPosition().y);
        objFont.drawString("Wave: " + String.valueOf(waveNo), pix, Window.getHeight()-
                super.getBackground().getHeight()+FONT_OFFSET);
        colour = new DrawOptions();
        if (ShadowDefend.getTimescale() >= TIMESCALELIM) {
            colour.setBlendColour(new Colour(0, MAX_COLOUR, 0));
        }
        else {
            colour.setBlendColour(new Colour(MAX_COLOUR, 0, 0));
        }
        pix += ITEM_OFFSET;
        objFont.drawString("Timescale: " + String.valueOf(ShadowDefend.getTimescale()), pix,
                Window.getHeight()-super.getBackground().getHeight()+FONT_OFFSET, colour);
        pix += ITEM_OFFSET;
        objFont.drawString("Status: " + status, pix + ITEM_OFFSET,Window.getHeight()-
                super.getBackground().getHeight()+FONT_OFFSET);
        pix += ITEM_OFFSET;
        objFont.drawString("Lives: " + String.valueOf(lives), pix + ITEM_OFFSET,Window.getHeight()-
                super.getBackground().getHeight()+FONT_OFFSET);
    }
}
