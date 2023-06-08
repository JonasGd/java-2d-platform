package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import renderer.DebugDraw;

@Getter @Setter
public class CircleCollider extends Collider {
    private float radius = 1f;

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(this.offset);
        DebugDraw.addCircle(center, this.radius);
    }
}
