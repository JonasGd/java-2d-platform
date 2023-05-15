package physics2Dtut.forces;

import physics2Dtut.rigidbody.Rigidbody2D;

public interface ForceGenerator {
    void updateForce(Rigidbody2D body, float dt);
}
