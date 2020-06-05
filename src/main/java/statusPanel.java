package main.java;

import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;

public class statusPanel extends Panel {
    private Font objFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private int PIX = 0;
    private DrawOptions colour;

    public statusPanel(Image backgroundImg, Point point) {
        super(backgroundImg, point);
    }

    public void render(int waveNo, String status, int lives) {
        super.getBackground().draw(super.getPosition().x, super.getPosition().y);
        objFont.drawString("Wave: " + String.valueOf(waveNo), PIX, Window.getHeight()-
                super.getBackground().getHeight()+20);
        colour = new DrawOptions();
        if (ShadowDefend.getTimescale() >= 1) {
            colour.setBlendColour(new Colour(0, 255, 0));
        }
        else {
            colour.setBlendColour(new Colour(255, 0, 0));
        }
        objFont.drawString("Timescale: " + String.valueOf(ShadowDefend.getTimescale()), PIX + 200,
                Window.getHeight()-super.getBackground().getHeight()+20, colour);
        objFont.drawString("Status: " + status, PIX + 500,Window.getHeight()-
                super.getBackground().getHeight()+20);
        objFont.drawString("Lives: " + String.valueOf(lives), PIX + 950,Window.getHeight()-
                super.getBackground().getHeight()+20);
    }
}
