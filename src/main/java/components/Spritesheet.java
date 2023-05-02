package components;

import lombok.Getter;
import org.joml.Vector2f;
import renderer.Texture;
import util.SpritePojo;
import util.SpritesheetInfo;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    private Texture texture;
    @Getter
    private List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing) {
        this.sprites = new ArrayList<>();

        this.texture = texture;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for (int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight) / (float) texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
            float leftX = currentX / (float) texture.getWidth();
            float bottomY = currentY / (float) texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };
            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTexCoords(texCoords);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()){
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }

    public Spritesheet(Texture texture, String location) {
        this.sprites = new ArrayList<>();

        this.texture = texture;

        SpritesheetInfo info = new SpritesheetInfo(location);
        List<SpritePojo> spritePojos = info.getSprites();

        for (SpritePojo spriteInfo : spritePojos) {
            float topY = (texture.getHeight() - spriteInfo.getY()) / (float) texture.getHeight();
            float rightX = (spriteInfo.getX() + spriteInfo.getWidth()) / (float) texture.getWidth();
            float leftX = spriteInfo.getX() / (float) texture.getWidth();
            float bottomY = (texture.getHeight() - spriteInfo.getY() - spriteInfo.getHeight()) / (float) texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };
            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTexCoords(texCoords);
            sprite.setHeight(spriteInfo.getHeight());
            sprite.setWidth(spriteInfo.getWidth());
            this.sprites.add(sprite);
        }
    }
}
