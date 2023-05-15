package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;

@Getter @Setter
public class Box2DCollider extends Component {
    private Vector2f halfSize = new Vector2f(1);
}
