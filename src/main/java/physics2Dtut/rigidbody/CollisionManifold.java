package physics2Dtut.rigidbody;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CollisionManifold {
    private boolean isColliding;
    private Vector2f normal;
    private List<Vector2f> contactPoints;
    private float depth;

    public CollisionManifold() {
        normal = new Vector2f();
        depth = 0.0f;
        isColliding = false;
    }
    public CollisionManifold(Vector2f normal, float depth) {
        this.normal = normal;
        this.contactPoints = new ArrayList<>();
        this.depth = depth;
        isColliding = false;
    }

    public void addContactPoint(Vector2f contact) {
        this.contactPoints.add(contact);
    }
}
