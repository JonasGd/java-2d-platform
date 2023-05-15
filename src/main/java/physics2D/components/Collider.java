package physics2D.components;

import components.Component;
import lombok.Getter;
import org.joml.Vector2f;

@Getter
public abstract class Collider extends Component {
    private Vector2f offset = new Vector2f();
}
