package main.java;
import bagel.util.Point;
import bagel.DrawOptions;
import java.util.List;
import static java.lang.System.exit;

public class Wave {
    private final int MAX_SLICER_COUNT = 5;
    //5 second delay
    private final double TIME_DELAY_DEFAULT = 300;
    private double timescaleMultiplier;
    private double timeDelay;
    private boolean isHappening;
    private Slicer[] activeSlicers = new Slicer[MAX_SLICER_COUNT];

    public boolean isHappening() {
        return isHappening;
    }

    public double getTimescaleMultiplier() {
        return timescaleMultiplier;
    }

    public void setTimescaleMultiplier(double timescaleMultiplier) {
        this.timescaleMultiplier = timescaleMultiplier;
    }

    public Slicer[] getActiveSlicers() {
        return activeSlicers;
    }

    //returns whether if wave has finished running
    public boolean Completed(Slicer[] activeSlicers) {
        int count = 0;
        for (Slicer slicer : activeSlicers) {
            if (slicer!=null && slicer.hasFinishedRun()) {
                count++;
            }
        }
        return count == MAX_SLICER_COUNT;
    }

    //starts the wave of the game
    public void Start() {
        //sets off the slicer
        this.timescaleMultiplier = 1;
        activeSlicers[0] = new Slicer("res/images/slicer.png");
        activeSlicers[0].spawn(this.timescaleMultiplier);
        this.timeDelay = TIME_DELAY_DEFAULT;
        this.isHappening = true;
    }

    //updates the wave whenever the update method in the ShadowDefend class is called
    public void Update() {
        for (int i = 0; i < activeSlicers.length; i++) {
            //slider not yet spawned
            if (activeSlicers[i] == null) {
                //checks if the previous slicer has sufficient distance such that it can be spawned
               if (activeSlicers[i-1] != null && activeSlicers[i - 1].getDistTravelled() >= this.timeDelay) {
                    activeSlicers[i] = new Slicer("res/images/slicer.png");
                    activeSlicers[i].spawn(this.timescaleMultiplier);
               }
           }
            //if all slicers have finished the map, exit game
            else if (this.Completed(activeSlicers)) {
                exit(0);
            }
            //if the current slicer has finished, ignore it, skip
            else if (Completed(activeSlicers)) {
                continue;
            }
            else {
                //moves slicer to new position, changes the angle
                activeSlicers[i].adjustPos(activeSlicers[i].getCurrSegment(), this.timescaleMultiplier);
                //obtains the angle of the slicer - required for the draw function
                DrawOptions angle = new DrawOptions().setRotation(activeSlicers[i].getAngle());
                System.out.println(activeSlicers[i].getAngle());
                activeSlicers[i].draw(activeSlicers[i].getCurrentPos().x, activeSlicers[i].getCurrentPos().y, angle);
            }
        }
    }
}


