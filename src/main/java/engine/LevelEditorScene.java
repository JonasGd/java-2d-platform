package engine;

import components.Rigidbody;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/tiles_spritesheet.png");
        if(levelLoaded) {
            this.activeGameObject = gameObjects.get(1);
            return;
        }

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100,100), new Vector2f(256,256)),2);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setSprite(sprites.getSprites().get(5));
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400,100), new Vector2f(256,256)), -1);
        SpriteRenderer obj2Sprite = new SpriteRenderer();
        obj2Sprite.setSprite(sprites.getSprites().get(1));
        obj2.addComponent(obj2Sprite);
        obj2.addComponent(new Rigidbody());
        this.addGameObjectToScene(obj2);
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheets/tiles_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/tiles_spritesheet.png"),
                        "assets/images/spritesheets/tiles_spritesheet.xml"));
    }

    private int spriteIndex = 5;
    private float spriteFlipTime = 0.1f;
    private float spriteFlipTimeLeft = 0.0f;

    @Override
    public void update(float dt) {
        /*
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex ++;
            if (spriteIndex > 14) {
                spriteIndex = 5;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprites().get((spriteIndex)));
        }
*/
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprites().get(i);
            float spriteWidth = sprite.getWidth() / 2;
            float spriteHeight = sprite.getHeight() / 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                System.out.println("Button " + i + " clicked");
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            Sprite nextSprite = i+1<sprites.size()? sprites.getSprites().get(i + 1): sprite;
            float nextSpriteWidth = nextSprite.getWidth() / 2;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + nextSpriteWidth*1.5f;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
