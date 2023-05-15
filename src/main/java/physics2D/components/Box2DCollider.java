package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;

@Getter
public class Box2DCollider extends Collider {
    @Setter
    private Vector2f halfSize = new Vector2f(1);
    private Vector2f origin = new Vector2f();
}
