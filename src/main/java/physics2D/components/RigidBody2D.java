package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;
import physics2D.enums.BodyType;

@Getter @Setter
public class RigidBody2D extends Component {
    private Vector2f velocity = new Vector2f();
    private float angularDamping = 0.8f;
    private float linearDamping = 0.9f;
    private float mass = 0;
    private BodyType bodyType = BodyType.Dynamic;

    private boolean fixedRotation = false;
    private boolean continuousCollision = true;

    private transient Body rawBody = null;

    @Override
    public void update(float dt) {
        if (rawBody != null) {
            this.gameObject.transform.position.set(
                    rawBody.getPosition().x, rawBody.getPosition().y
            );
            this.gameObject.transform.rotation = rawBody.getAngle();
        }
    }
}