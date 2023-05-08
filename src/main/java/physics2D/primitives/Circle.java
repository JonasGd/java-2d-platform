package physics2D.primitives;

import lombok.Getter;
import org.joml.Vector2f;
import physics2D.rigidbody.Rigidbody2D;

public class Circle {
    @Getter
    private float radius = 1.0f;
    private Rigidbody2D body = null;

    public Vector2f getCenter() {
        return body.getPosition();
    }
}
