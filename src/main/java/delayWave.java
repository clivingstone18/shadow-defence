package main.java;

public class delayWave extends Wave {
    private int waveNo;
    private double duration;
    private double current;
    private boolean isHappening;
    private boolean hasFinished;
    private double FPS = 60;
    //measures duration in ms
    //measure of duration

    @Override
    public void Update() {
        if (current <= duration) {
            current += ShadowDefend.getTimescale()/FPS;
        } else {
            isHappening = false;
            hasFinished = true;
        }
    }

    public delayWave(double duration, int WaveNo) {
        this.duration = duration/1000;
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
        hasFinished = false;

    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
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

