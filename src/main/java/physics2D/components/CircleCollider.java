package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CircleCollider extends Collider {
    private float radius = 1f;
}
