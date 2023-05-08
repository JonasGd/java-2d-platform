package util;

import org.joml.Vector2f;

public class EMath {

    public static void rotate(Vector2f vec, float angle, Vector2f origin) {
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);

        float xPrime = x * cos - y * sin;
        float yPrime = x * sin + y * cos;

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }

    public static boolean compare(float x, float y, float epsilon) {
        return Math.abs(x - y) <= epsilon * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2, float epsilon) {
        return compare(vec1.x, vec2.x, epsilon) && compare(vec1.y, vec2.y, epsilon);
    }

    public static boolean compare(float x, float y) {
        return compare(x,y,Float.MIN_VALUE);
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2) {
        return compare(vec1, vec2, Float.MIN_VALUE);
    }
}
