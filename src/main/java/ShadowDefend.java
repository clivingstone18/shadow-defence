import bagel.*;
import bagel.util.Vector2;

class ShadowDefend extends AbstractGame {

    private Wave wave;
    private Map map;
    private buyPanel buyPanel;
    private int funds;

    public ShadowDefend() {
        wave = new Wave();
        map = new Map();
        funds = 500;
    }

    @Override
    protected void update(Input input) {
        buyPanel.render();


        //renders the map
        map.render();
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


