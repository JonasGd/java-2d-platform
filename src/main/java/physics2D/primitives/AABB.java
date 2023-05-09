package physics2D.primitives;

import lombok.Setter;
import org.joml.Vector2f;
import physics2D.rigidbody.Rigidbody2D;

//Axis Aligned Bounding Box
public class AABB {
    private Vector2f center = new Vector2f();
    private Vector2f halfSize;
    private Vector2f size = new Vector2f();
    @Setter
    private Rigidbody2D rigidbody = null;

    public AABB() {
        this.halfSize = new Vector2f(size.mul(0.5f));
    }
    public AABB(Vector2f min, Vector2f max) {
        this();
        this.size = new Vector2f(max).sub(min);
    }

    public Vector2f getMin() {
        return new Vector2f(this.rigidbody.getPosition()).sub(this.halfSize);
    }
    public Vector2f getMax() {
        return new Vector2f(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public void setSize(Vector2f size) {
        this.size.set(size);
        this.halfSize.set(size.x /2.0f, size.y / 2.0f);
    }


}
