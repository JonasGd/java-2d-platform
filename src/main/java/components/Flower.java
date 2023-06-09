package components;

import engine.GameObject;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import physics2D.components.RigidBody2D;
import util.AssetPool;

public class Flower extends Component{
    private RigidBody2D rb;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(RigidBody2D.class);
        AssetPool.getSound("assets/sounds/power_up.ogg").play();
        this.rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.powerup();
            this.gameObject.destroy();
        }
    }
}
