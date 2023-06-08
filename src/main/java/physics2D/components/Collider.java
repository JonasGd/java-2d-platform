package physics2D.components;

import components.Component;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;

@Getter
public abstract class Collider extends Component {
    protected Vector2f offset = new Vector2f();

    public void setOffset(Vector2f newOffset) {this.offset.set(newOffset);}
}
