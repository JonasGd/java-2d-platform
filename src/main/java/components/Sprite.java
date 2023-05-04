package components;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import renderer.Texture;

@Getter @Setter
public class Sprite {

    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1,1),
            new Vector2f(1,0),
            new Vector2f(0,0),
            new Vector2f(0,1)
    };
    private float height = 1.0f, width = 1.0f;

    public int getTexId() {
        return texture == null ? -1 : texture.getTexID();
    }

}
