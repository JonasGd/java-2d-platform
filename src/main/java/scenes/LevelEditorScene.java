package scenes;

import components.*;
import engine.Camera;
import engine.GameObject;
import engine.Prefabs;
import engine.Transform;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderer.DebugDraw;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites, spritesTiles;
    SpriteRenderer obj1Sprite;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/p1_spritesheet.png");
        spritesTiles = AssetPool.getSpritesheet("assets/images/spritesheets/tiles_spritesheet.png");
        if(levelLoaded) {
            if (gameObjects.size() > 0)
                this.activeGameObject = gameObjects.get(0);
            return;
        }

//        obj1 = new GameObject("Object 1", new Transform(new Vector2f(200,100),
//                new Vector2f(256,256)),2);
//        obj1Sprite = new SpriteRenderer();
//        obj1Sprite.setColor(new Vector4f(1,0,0,0.4f));
//        obj1.addComponent(obj1Sprite);
//        obj1.addComponent(new Rigidbody());
//        this.addGameObjectToScene(obj1);
//        this.activeGameObject = obj1;
//
//        GameObject obj2 = new GameObject("Object 2",
//                new Transform(new Vector2f(400,100), new Vector2f(256,256)), 1);
//        SpriteRenderer obj2Sprite = new SpriteRenderer();
//        obj2Sprite.setSprite(sprites.getSprite(1));
//        obj2.addComponent(obj2Sprite);
//        this.addGameObjectToScene(obj2);
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheets/p1_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/p1_spritesheet.png"),
                        "assets/images/spritesheets/p1_spritesheet.txt"));
        AssetPool.addSpritesheet("assets/images/spritesheets/tiles_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/tiles_spritesheet.png"),
                        "assets/images/spritesheets/tiles_spritesheet.xml"));

        for (GameObject g : gameObjects) {
            if (g.getComponent(SpriteRenderer.class)!= null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null){
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }
    }

    float x = 0.0f;
    float y = 0.0f;
    @Override
    public void update(float dt) {
        levelEditorStuff.update(dt);
        DebugDraw.addCircle(new Vector2f(x,y), 64, new Vector3f(1,0,0),1);
        x += 50f * dt;
        y += 50f * dt;

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
        for (int i = 0; i < spritesTiles.size(); i++) {
            Sprite sprite = spritesTiles.getSprite(i);
            float spriteWidth = sprite.getWidth() / 2;
            float spriteHeight = sprite.getHeight() / 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                System.out.println(spriteWidth + ":" + spriteHeight);
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            Sprite nextSprite = i+1<spritesTiles.size()? spritesTiles.getSprite(i+1): sprite;
            float nextSpriteWidth = nextSprite.getWidth() / 2;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + nextSpriteWidth*1.5f;
            if (i + 1 < spritesTiles.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
