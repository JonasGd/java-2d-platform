package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
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
        if(levelLoaded) {
            return;
        }

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100,100), new Vector2f(256,256)),2);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setSprite(sprites.getSprites().get(5));
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400,100), new Vector2f(256,256)), -1);
        SpriteRenderer obj2Sprite = new SpriteRenderer();
        obj2Sprite.setSprite(sprites.getSprites().get(1));
        obj2.addComponent(obj2Sprite);
        this.addGameObjectToScene(obj2);
        this.activeGameObject = obj2;
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/p1_spritesheet.png"),
                        "assets/images/p1_spritesheet.txt"));
    }

    private int spriteIndex = 5;
    private float spriteFlipTime = 0.1f;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex ++;
            if (spriteIndex > 14) {
                spriteIndex = 5;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprites().get((spriteIndex)));
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImGui.text("Some random text");
        ImGui.end();
    }
}
