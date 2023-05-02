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
    private int height = 1, width = 1;

//    public Sprite(Texture texture) {
//        this.texture = texture;
//        Vector2f[] texCoords = {
//                new Vector2f(1,1),
//                new Vector2f(1,0),
//                new Vector2f(0,0),
//                new Vector2f(0,1)
//        };
//        this.texCoords = texCoords;
//    }
//
//    public Sprite(Texture texture, Vector2f[] texCoords) {
//        this.texture = texture;
//        this.texCoords = texCoords;
//    }
//
//    public Sprite(Texture texture, Vector2f[] texCoords, int height, int width) {
//        this.texture = texture;
//        this.texCoords = texCoords;
//        this.height = height;
//        this.width = width;
//    }
}
