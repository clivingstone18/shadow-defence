package main.java;

import bagel.Image;
import bagel.Window;

public class statusPanel {
    private Image background = new Image("res/images/statuspanel.png");

    public void render() {
        background.drawFromTopLeft(0, Window.getHeight()-background.getHeight());
    }

}
