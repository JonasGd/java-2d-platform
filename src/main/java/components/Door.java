package components;

import engine.Direction;
import engine.GameObject;
import engine.KeyListener;
import engine.Window;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import util.AssetPool;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;

public class Door extends Component{
    private Direction direction;
    private String connectingPipeName = "";
    private boolean isEntrance = false;
    private transient GameObject connectingPipe = null;
    private transient float entranceVectorTolerance = 0.6f;
    private transient PlayerController collindingPlayer = null;

    public Door(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void start() {
        connectingPipe = Window.getScene().getGameObject(connectingPipeName);
    }

    @Override
    public void update(float dt) {
        if (connectingPipe == null) return;
        if (collindingPlayer != null) {
            boolean playerEntering = false;
            switch (direction) {
                case Up:
                    playerEntering = true;
                    break;
            }
            if (playerEntering) {
                if ((KeyListener.keyBeginPress(GLFW_KEY_DOWN) || KeyListener.keyBeginPress(GLFW_KEY_S)) && isEntrance /*&& playerAtEntrance()*/) {
                    collindingPlayer.setPosition(getPlayerPosition(connectingPipe));
                    AssetPool.getSound("assets/sounds/pipe.ogg").play();
                }
            }
        }
    }

    public boolean playerAtEntrance() {
        if (collindingPlayer == null) {
            return false;
        }

        Vector2f min = new Vector2f(gameObject.transform.position).sub(new Vector2f(gameObject.transform.scale).mul(0.5f));
        Vector2f max = new Vector2f(gameObject.transform.position).add(new Vector2f(gameObject.transform.scale).mul(0.5f));
        Vector2f playerMin = new Vector2f(collindingPlayer.gameObject.transform.position).sub(new Vector2f(collindingPlayer.gameObject.transform.scale).mul(0.5f));
        Vector2f playerMax = new Vector2f(collindingPlayer.gameObject.transform.position).add(new Vector2f(collindingPlayer.gameObject.transform.scale).mul(0.5f));

        switch (direction) {
            case Up:
                return playerMin.y >= max.y && playerMax.x > min.x && playerMin.x < max.x;
        }

        return false;
    }

    @Override
    public void beginCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null) {
            collindingPlayer = playerController;
        }
    }

    @Override
    public void endCollision(GameObject collidingObject, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = collidingObject.getComponent(PlayerController.class);
        if (playerController != null) {
            collindingPlayer = null;
        }
    }

    private Vector2f getPlayerPosition(GameObject door) {
        Door doorComponent = door.getComponent(Door.class);
        switch (doorComponent.direction) {
            case Up:
                return new Vector2f(door.transform.position).add(0.0f,0.0f);
        }
        return new Vector2f();
    }
}
