package physics2D.components;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import renderer.DebugDraw;

@Getter
public class Box2DCollider extends Collider {
    @Setter
    private Vector2f halfSize = new Vector2f(0.25f);
    private Vector2f origin = new Vector2f();

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(this.offset);
        DebugDraw.addBox2D(center, this.halfSize, this.gameObject.transform.rotation);
    }
}
