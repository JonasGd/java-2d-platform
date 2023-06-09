package physics2D.components;

import components.Component;
import engine.Window;
import lombok.Getter;
import lombok.Setter;
import org.jbox2d.common.Vec2;
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
    private float friction = 0.1f;
    public float angularVelocity = 0.0f;
    public float gravityScale = 1.0f;
    private boolean isSensor = false;

    private boolean fixedRotation = false;
    private boolean continuousCollision = true;

    private transient Body rawBody = null;

    public void addVelocity(Vector2f forceToAdd) {
        if (rawBody != null)
           rawBody.applyForceToCenter(new Vec2(forceToAdd.x, forceToAdd.y));
    }

    public void addImpulse(Vector2f impulse) {
        if (rawBody != null)
            rawBody.applyLinearImpulse(new Vec2(impulse.x, impulse.y), rawBody.getWorldCenter());
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity.set(velocity);
        if (rawBody != null)
            this.rawBody.setLinearVelocity(new Vec2(velocity.x, velocity.y));
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
        if (rawBody != null)
            this.rawBody.setAngularVelocity(angularVelocity);
    }

    public void setGravityScale(float gravityScale) {
        this.gravityScale = gravityScale;
        if (rawBody != null)
            this.rawBody.setGravityScale(gravityScale);
    }

    public void setIsSensor() {
        isSensor = true;
        if (rawBody != null) {
            Window.getPhysics().setIsSensor(this);
        }
    }

    public void setNotSensor() {
        isSensor = false;
        if (rawBody != null) {
            Window.getPhysics().setIsSensor(this);
        }
    }

    public void setPosition(Vector2f newPos) {
        if (rawBody != null) {
            rawBody.setTransform(new Vec2(newPos.x, newPos.y), gameObject.transform.rotation);
        }
    }

    @Override
    public void update(float dt) {
        if (rawBody != null) {
            if (this.bodyType == BodyType.Dynamic || this.bodyType == BodyType.Kinematic) {
                this.gameObject.transform.position.set(
                        rawBody.getPosition().x, rawBody.getPosition().y
                );
                this.gameObject.transform.rotation = rawBody.getAngle();
                Vec2 val = rawBody.getLinearVelocity();
                this.velocity.set(val.x, val.y);
            } else if (this.bodyType == BodyType.Static) {
                this.rawBody.setTransform(new Vec2(this.gameObject.transform.position.x, this.gameObject.transform.position.y),
                        this.gameObject.transform.rotation);
            }
        }
    }
}
