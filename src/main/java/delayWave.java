package main.java;

public class delayWave extends Wave {
    private int waveNo;
    private double duration;
    private double current;
    private boolean isHappening;
    private boolean hasCompleted;
    private double FPS = 60;

    @Override
    public void Update() {
        if (current <= duration) {
            current += ShadowDefend.getTimescale()/FPS;
        } else {
            isHappening = false;
            hasCompleted = true;
        }
    }

    public delayWave(double duration, int WaveNo) {
        this.duration = duration/1000;
        this.waveNo = WaveNo;
    }

    public int getWaveNo() {
        return waveNo;
    }


    public String toString() {
        System.out.println("delay wave");
        System.out.println(waveNo);
        return null;
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
    public boolean hasCompleted() {
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

