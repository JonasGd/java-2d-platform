package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CircleCollider extends Component {
    private float radius = 1f;
}
