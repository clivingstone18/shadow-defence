package main.java;

import bagel.*;
import bagel.util.Vector2;
import bagel.Window;

class ShadowDefend extends AbstractGame {

    private Wave wave;
    private Map map;
    private buyPanel buyPanel;
    private statusPanel statusPanel;
    private int playerFunds;
    private Font font;

    public ShadowDefend() {
        wave = new Wave();
        map = new Map();
        playerFunds = 500;
        buyPanel = new buyPanel();
        statusPanel = new statusPanel();
        font = new Font("res/fonts/DejaVuSans-Bold.ttf", 18);
    }

    @Override
    protected void update(Input input) {
        map.render();
        buyPanel.render();
        buyPanel.updateBuyPanel(playerFunds);
        statusPanel.render();
        //renders the map
        //checks for input to begin wave
        if (input.wasPressed(Keys.S) && !wave.isHappening()) {
            wave.Start();
        } else if (wave.isHappening()) {
            //detects keyboard input for changes to timescalemultiplier value
            if (input.wasPressed(Keys.K) || input.wasPressed(Keys.L)) {
                double prevTSM = wave.getTimescaleMultiplier();
                if (input.wasPressed(Keys.L)) {
                    wave.setTimescaleMultiplier(prevTSM + 1);
                } else {
                    if (prevTSM > 1) {
                        wave.setTimescaleMultiplier(prevTSM - 1);
                    }
                }
                for (Slicer slicer : wave.getActiveSlicers()) {
                    if (slicer != null) {
                        //update vectors to account for magnitude
                        Vector2 prevDirVec = slicer.getDirVec();
                        slicer.setDirVec(prevDirVec.mul(wave.getTimescaleMultiplier()/prevTSM));
                    }
                }
            }
            //updates the wave ready for the next update
            wave.Update();
        }
    }

    public static void main(String[] args) {
        new ShadowDefend().run();

    }
}


