package main.java;

public class delayWave extends Wave {
    private int waveNo;
    private double duration;
    private double current;
    private boolean isHappening;
    private boolean hasCompleted;
    private final double FPS = 60;
    private final double FACTOR = 1000;

    @Override
    public void Update() {
        if (current <= duration) {
            current += ShadowDefend.timescaleMultiplier/FPS;
        } else {
            isHappening = false;
            hasCompleted = true;
        }
    }

    public delayWave(double duration, int WaveNo) {
        this.duration = duration/FACTOR;
        this.waveNo = WaveNo;
    }

    public int getWaveNo() {
        return waveNo;
    }



    @Override
    public boolean isHappening() {
        return isHappening;
    }

    @Override
    public void Start() {
        isHappening = true;
        current = 0;
        hasCompleted = false;

    }

    @Override
    public boolean hasFinished() {
        return hasCompleted;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    @Override
    public double getCurrTime() {
        return current;
    }

}

