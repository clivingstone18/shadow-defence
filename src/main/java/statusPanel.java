package main.java;

import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;

public class statusPanel {
    private Image background = new Image("res/images/statuspanel.png");
    private Font objFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 14);
    private int PIX = 0;
    private DrawOptions colour;

    public void render(int waveNo, double timeScale, String status, int lives) {
        background.drawFromTopLeft(0, Window.getHeight()-background.getHeight());
        objFont.drawString("Wave: " + String.valueOf(waveNo), PIX, Window.getHeight()-background.getHeight()+20);
        colour = new DrawOptions();
        if (timeScale >= 1) {
            colour.setBlendColour(new Colour(0, 255, 0));
        }
        else {
            colour.setBlendColour(new Colour(255, 0, 0));
        }
        objFont.drawString("Timescale: " + String.valueOf(timeScale), PIX + 200, 
                Window.getHeight()-background.getHeight()+20, colour);
        objFont.drawString("Status: " + status, PIX + 500,Window.getHeight()-background.getHeight()+20);
        objFont.drawString("Lives: " + String.valueOf(lives), PIX + 950,Window.getHeight()-background.getHeight()+20);

    }

}
