package physics2D;

import org.joml.Vector2f;
import physics2D.forces.ForceRegistry;
import physics2D.forces.Gravity2D;
import physics2D.rigidbody.Rigidbody2D;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem2D {
    private ForceRegistry forceRegistry;
    private List<Rigidbody2D> rigidbodies;
    private Gravity2D gravity;
    private float fixedUpdate;

    public PhysicsSystem2D(float fixedUpdateDt, Vector2f gravity) {
        forceRegistry = new ForceRegistry();
        rigidbodies = new ArrayList<>();
        this.gravity = new Gravity2D(gravity);
        fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt) {
        fixedUpdate();
    }

    public void fixedUpdate() {
        forceRegistry.updateForces(fixedUpdate);

        //update the velocities of all rigidbodies
        for (int i = 0; i < rigidbodies.size(); i++) {
            rigidbodies.get(i).physicsUpdate(fixedUpdate);
        }
    }

    public void addRigidBody(Rigidbody2D body) {
        this.rigidbodies.add(body);
        this.forceRegistry.add(body, gravity);
    }
}
