package main.java;
import bagel.util.Vector2;
import java.lang.Math;
//extension of bagel vector class to allow calculation of angle between vectors
public class CustomVector extends Vector2 {

    public static double AngleBetweenVectors(Vector2 vector1, Vector2 vector2) {
        double dotProduct = vector1.dot(vector2);
        double magnitudeProduct = vector1.length() * vector2.length();
        if (vector2.y < 0) {
            return 2*3.14 - Math.acos(dotProduct/magnitudeProduct);
        }
        else {
            return Math.acos(dotProduct/magnitudeProduct);
        }
    }

}


