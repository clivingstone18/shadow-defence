package main.java;
import bagel.Image;
import bagel.util.Point;
import java.util.List;
import bagel.util.Vector2;

public class Slicer extends Image {
    private final List<Point> polylinePoints = new Map().getPolylinePoints();
    private final Point firstPoint = polylinePoints.get(0);
    private LineSegment currSegment;
    private int lsIndex;
    private boolean hasFinished;
    private Point currentPos;
    private Vector2 dirVec;
    private double angle;
    private double distTravelled;
    private boolean spawned;

    //GETTERS AND SETTERS

    public LineSegment getCurrSegment() {
        return currSegment;
    }

    public boolean hasFinishedRun() {
        return hasFinished;
    }

    public Point getCurrentPos() {
        return currentPos;
    }

    public Vector2 getDirVec() {
        return dirVec;
    }

    public double getDistTravelled() {
        return distTravelled;
    }

    public double getAngle() {
        return angle;
    }

    //constructor for the slicer
    public Slicer(String filename) {
        super(filename);
    }

    //spawns the slicer
    public void spawn(double timescaleMultiplier) {
        this.currentPos = firstPoint;
        Point secondPoint = polylinePoints.get(1);
        this.currSegment = new LineSegment(firstPoint, secondPoint);
        this.lsIndex = 0;
        this.spawned = true;
        this.distTravelled = 0;
        this.angle = 0;
        this.dirVec = new Vector2(timescaleMultiplier, 0);
    }

    public void setDirVec(Vector2 vector) {
        this.dirVec = vector;
    }

    public void adjustPos(LineSegment linesegment, double timescaleMultiplier) {
        //theoretical next point - just has to be verified, otherwise dirVec must change so slicer remains on path
        //checks condition - if slicer were move forward would it lie on the current polyline segment?
        if (LineSegment.PointOnLineSegment(linesegment, (this.currentPos.asVector().add(this.dirVec)).asPoint())){
            this.currentPos = (this.currentPos.asVector().add(this.dirVec)).asPoint();
            this.distTravelled += timescaleMultiplier;
        }
        else {
            //updates new line segment
            this.lsIndex++;
            //if haven't reached the end of the maze
            if (this.lsIndex < polylinePoints.size()-1) {
                this.currSegment.start = linesegment.end;
                this.currSegment.end = polylinePoints.get(this.lsIndex + 1);
                setDirVec(this.currSegment.getDirection(timescaleMultiplier));
                this.currentPos = this.currSegment.start;
                this.distTravelled += timescaleMultiplier;
                //changes angle relative to (1,0)
                this.angle = CustomVector.AngleBetweenVectors(new Vector2(1,0), this.dirVec);
            }
            else {
                this.hasFinished = true;
            }
        }
    }
}


