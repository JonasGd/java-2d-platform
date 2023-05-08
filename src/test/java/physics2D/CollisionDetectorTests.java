package physics2D;

import org.joml.Vector2f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import physics2D.rigidbody.IntersectionDetector2D;
import renderer.Line2D;


public class CollisionDetectorTests {
    private final float EPSILON = 0.000001f;

    @Test
    public void beginPointOnLine2DShouldReturnTrue(){
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(12,4));
        Vector2f point = new Vector2f(0,0);

        Assertions.assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void EndPointOnLine2DShouldReturnTrue(){
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(12,4));
        Vector2f point = new Vector2f(12,4);

        Assertions.assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void pointOnVerticalLine2DShouldReturnTrue(){
        Line2D line = new Line2D(new Vector2f(0,0), new Vector2f(0,10));
        Vector2f point = new Vector2f(0,5);

        Assertions.assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

}
