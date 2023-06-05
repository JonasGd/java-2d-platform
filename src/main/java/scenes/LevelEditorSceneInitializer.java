package scenes;

import components.*;
import engine.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorSceneInitializer extends SceneInitializer {

    private Spritesheet sprites, spritesTiles;

    private GameObject levelEditorStuff;

    public LevelEditorSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/p1_spritesheet.png");
        spritesTiles = AssetPool.getSpritesheet("assets/images/spritesheets/tiles_spritesheet.png");
        Spritesheet gizmos = AssetPool.getSpritesheet("assets/images/gizmos.png");

        levelEditorStuff = scene.createGameObject("LevelEditor");
        levelEditorStuff.setNoSerialize();
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());
        levelEditorStuff.addComponent(new EditorCamera(scene.camera()));
        levelEditorStuff.addComponent(new GizmoSystem(gizmos));
        scene.addGameObjectToScene(levelEditorStuff);
    }

    @Override
    public void loadResources(Scene scene){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheets/p1_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/p1_spritesheet.png"),
                        "assets/images/spritesheets/p1_spritesheet.txt"));
        AssetPool.addSpritesheet("assets/images/spritesheets/tiles_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/tiles_spritesheet.png"),
                        "assets/images/spritesheets/tiles_spritesheet.xml"));
        AssetPool.addSpritesheet("assets/images/gizmos.png",
                new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),
                        24,48, 3,0));

        for (GameObject g : scene.getGameObjects()) {
            if (g.getComponent(SpriteRenderer.class)!= null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null){
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }

            if (g.getComponent(StateMachine.class)!= null) {
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }
    }

        @Override
    public void imgui() {
        ImGui.begin("Level Editor Stuff");
        levelEditorStuff.imgui();
        ImGui.end();

        ImGui.begin("Objects");

        if(ImGui.beginTabBar("WindowTabBar")) {
            if(ImGui.beginTabItem("Blocks")) {
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
                        GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                        levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                    }
                    ImGui.popID();

                    ImVec2 lastButtonPos = new ImVec2();
                    ImGui.getItemRectMax(lastButtonPos);
                    float lastButtonX2 = lastButtonPos.x;
                    Sprite nextSprite = i + 1 < spritesTiles.size() ? spritesTiles.getSprite(i + 1) : sprite;
                    float nextSpriteWidth = nextSprite.getWidth() / 2;
                    float nextButtonX2 = lastButtonX2 + itemSpacing.x + nextSpriteWidth * 1.5f;
                    if (i + 1 < spritesTiles.size() && nextButtonX2 < windowX2) {
                        ImGui.sameLine();
                    }
                }
                ImGui.endTabItem();
            }

            if(ImGui.beginTabItem("Prefabs")){
                Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/spritesheets/p1_spritesheet.png");
                Sprite sprite = playerSprites.getSprite(1);
                float spriteWidth = sprite.getWidth() / 2;
                float spriteHeight = sprite.getHeight() / 2;
                int id = sprite.getTexId();
                Vector2f[] texCoords = sprite.getTexCoords();

                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateJumper();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.sameLine();

                ImGui.endTabItem();
            }
            ImGui.endTabBar();
        }
        ImGui.end();
    }
}
