package main.java;

public abstract class Wave {
    public abstract void Update();
    public abstract boolean isHappening();
    public abstract void Start();

    public abstract boolean hasCompleted();
    public abstract int getWaveNo();

    public abstract double getDuration();

    public abstract double getCurrTime();
    public abstract String toString();
}
