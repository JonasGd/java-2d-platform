package physics2D.rigidbody;

import components.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector2fc;

@Getter @Setter
public class Rigidbody2D extends Component {
    private Vector2f position = new Vector2f();
    private float rotation = 0.0f;
}
