package main.java;

public abstract class Wave {

    private int waveNo;
    private double duration;
    private double current;
    private boolean isHappening;
    private boolean hasFinished;

    public void setWaveNo(int waveNo) {
        this.waveNo = waveNo;
    }

    public int getWaveNo() {
        return waveNo;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setCurrTime(double current) {
        this.current = current;
    }

    public void setHappening(boolean happening) {
        isHappening = happening;
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public double getDuration() {
        return duration;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public boolean isHappening() {
        return isHappening;
    }
    public double getCurrTime() {
        return current;
    }

    // Abstract methods
    public abstract void Start();
    public abstract void Update();

}
