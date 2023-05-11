package physics2D.primitives;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import physics2D.rigidbody.Rigidbody2D;

public class Circle extends Collider2D{
    @Getter @Setter
    private float radius = 1.0f;
    @Setter
    private Rigidbody2D rigidbody = null;

    public Vector2f getCenter() {
        return rigidbody.getPosition();
    }
}
