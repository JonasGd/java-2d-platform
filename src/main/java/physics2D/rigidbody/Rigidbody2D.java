package physics2D.rigidbody;

import components.Component;
import engine.Transform;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import physics2D.primitives.Collider2D;

public class Rigidbody2D extends Component {
    private Transform rawTransform;
    @Getter @Setter
    private Collider2D collider;

    @Getter
    private Vector2f position = new Vector2f();
    @Getter
    private float rotation = 0.0f;
    @Getter
    private float mass = 0.0f;
    @Getter
    private float inverseMass = 0.0f;

    private Vector2f forceAccum = new Vector2f();
    private Vector2f linearVelocity = new Vector2f();
    private float angularVelocity = 0.0f;
    private float linearDamping = 0.0f;
    private float angularDamping = 0.0f;

    //coefficient of restitution
    @Getter @Setter
    private float cor = 1.0f;

    private boolean fixedRotation = false;

    public void physicsUpdate(float dt) {
        if (this.mass == 0.0f) return;

        //Calculate linear velocity
        Vector2f acceleration = new Vector2f(forceAccum).mul(this.inverseMass);
        linearVelocity.add(acceleration.mul(dt));

        //Update the linear position
        this.position.add(new Vector2f(linearVelocity).mul(dt));

        syncCollisionTransforms();
        clearAccumulators();
    }

    public void syncCollisionTransforms() {
        if (rawTransform != null) {
            rawTransform.position.set(this.position);
        }
    }


    public void clearAccumulators(){
        this.forceAccum.zero();
    }

    public void setTransform(Vector2f position, float rotation) {
        this.position.set(position);
        this.rotation = rotation;
    }

    public void setTransform(Vector2f position) {
        this.position.set(position);
    }


    public void setMass(float mass) {
        this.mass = mass;
        if (this.mass != 0.0f) {
            this.inverseMass = 1.0f / this.mass;
        }
    }

    public void setVelocity(Vector2f velocity){
        this.linearVelocity.set(velocity);
    }

    public Vector2f getVelocity() {
        return this.linearVelocity;
    }

    public boolean hasInfiniteMass() {
        return this.mass == 0.0f;
    }

    public void addForce(Vector2f force) {
        this.forceAccum.add(force);
    }

    public void setRawTransform(Transform rawTransform) {
        this.rawTransform = rawTransform;
        this.position.set(rawTransform.position);
    }
}
