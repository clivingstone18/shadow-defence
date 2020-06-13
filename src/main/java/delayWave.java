package main.java;

public class delayWave extends Wave {
    private final double TIME_FACTOR = 1000;
    private final double FPS = 60;

    public delayWave(double duration, int waveNo) {
        super.setDuration(duration / TIME_FACTOR);
        super.setWaveNo(waveNo);
    }

    @Override
    public void Update() {
        if (super.getCurrTime() <= super.getDuration()) {
            super.setCurrTime(super.getCurrTime()+ShadowDefend.timescaleMultiplier/FPS);
        } else {
            super.setHappening(false);
            super.setHasFinished(true);
        }
    }

    @Override
    public void Start() {
        setHappening(true);
        setCurrTime(0);
        setHasFinished(false);
    }

}

