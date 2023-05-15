package physics2D;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Physics2D {
    private Vec2 gravity = new Vec2(0,-10.0f);
    private World world = new World(gravity);

    private float physicsTime = 0.0f;
    private float phyicsTimeStep = 1.0f / 60.0f;
    private int velocityIterations = 8;
    private int positionIterations = 3;

    public void update(float dt){
        physicsTime += dt;
        if (physicsTime >= 0.0f) {
            physicsTime -= phyicsTimeStep;
            world.step(phyicsTimeStep, velocityIterations, positionIterations);
        }
    }
}
