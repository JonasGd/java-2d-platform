package physics2D.primitives;

import lombok.Getter;
import org.joml.Vector2f;

@Getter
public class Ray2D {
    private Vector2f origin;
    private Vector2f direction;

    public Ray2D(Vector2f origin, Vector2f direction) {
        this.origin = origin;
        this.direction = direction;
        this.direction.normalize();
    }


}
