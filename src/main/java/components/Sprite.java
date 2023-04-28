package components;

import org.joml.Vector2f;
import renderer.Texture;

public class Sprite {

    private Texture texture;
    private Vector2f[] texCoords;
    private int height,width;

    public Sprite(Texture texture) {
        this.texture = texture;
        Vector2f[] texCoords = {
                new Vector2f(1,1),
                new Vector2f(1,0),
                new Vector2f(0,0),
                new Vector2f(0,1)
        };
        this.texCoords = texCoords;
    }

    public Sprite(Texture texture, Vector2f[] texCoords) {
        this.texture = texture;
        this.texCoords = texCoords;
    }

    public Sprite(Texture texture, Vector2f[] texCoords, int height, int width) {
        this.texture = texture;
        this.texCoords = texCoords;
        this.height = height;
        this.width = width;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
