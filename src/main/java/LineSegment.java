import java.lang.Math;
import static java.lang.Math.min;
import static java.lang.Math.max;
import bagel.util.Point;
import bagel.util.Vector2;
//segment of the polyline

public class LineSegment {
    Point start;
    Point end;
    double scaler;

    //setter method
    public LineSegment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    //returns whether a given point lies on the line segment
    public static boolean PointOnLineSegment(LineSegment linesegment, Point point) {
        return (((min(linesegment.start.x,linesegment.end.x) <= point.x && point.x <= (max(linesegment.start.x,linesegment.end.x))))&&
                ((min(linesegment.start.y,linesegment.end.y)) <= point.y && point.y <= (max(linesegment.start.y,linesegment.end.y))));
    }

    //returns the length of the line segment
    public static double getMagnitude(LineSegment linesegment) {
        return Math.sqrt(Math.pow(linesegment.end.x - linesegment.start.x, 2) +
                Math.pow(linesegment.end.y - linesegment.start.y, 2));
    }

    //returns the direction of the linesegment, providing a vector
    public Vector2 getDirection(double timescaleMultiplier) {
        //get magnitude

        double magnitude = getMagnitude(this);

        //x2-x1, y2-y1 for direction

        Vector2 dir = this.end.asVector().sub(this.start.asVector());

        //scale according to timescaleMultiplier
        this.scaler = timescaleMultiplier/magnitude;
        dir = dir.mul(scaler);
        return dir;
    }
}