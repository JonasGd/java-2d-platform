package scenes;

import components.*;
import engine.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import physics2D.components.Box2DCollider;
import physics2D.components.RigidBody2D;
import physics2D.enums.BodyType;
import physics2Dtut.primitives.Box2D;
import util.AssetPool;

import java.io.File;
import java.util.Collection;

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
        levelEditorStuff.addComponent(new KeyControls());
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
        AssetPool.addSpritesheet("assets/images/spritesheets/p2_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/p2_spritesheet.png"),
                        "assets/images/spritesheets/p2_spritesheet.txt"));
        AssetPool.addSpritesheet("assets/images/spritesheets/p3_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/p3_spritesheet.png"),
                        "assets/images/spritesheets/p3_spritesheet.txt"));
        AssetPool.addSpritesheet("assets/images/spritesheets/enemies_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/enemies_spritesheet.png"),
                        "assets/images/spritesheets/enemies_spritesheet.txt"));
        AssetPool.addSpritesheet("assets/images/spritesheets/tiles_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/tiles_spritesheet.png"),
                        "assets/images/spritesheets/tiles_spritesheet.xml"));
        AssetPool.addSpritesheet("assets/images/spritesheets/items_spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/items_spritesheet.png"),
                        "assets/images/spritesheets/items_spritesheet.xml"));
        AssetPool.addSpritesheet("assets/images/gizmos.png",
                new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),
                        24,48, 3,0));

        AssetPool.addSound("assets/sounds/main-theme.ogg", true);
        AssetPool.addSound("assets/sounds/1-up.ogg", false);
        AssetPool.addSound("assets/sounds/break_block.ogg", false);
        AssetPool.addSound("assets/sounds/bump.ogg", false);
        AssetPool.addSound("assets/sounds/coin.ogg", false);
        AssetPool.addSound("assets/sounds/death.ogg", false);
        AssetPool.addSound("assets/sounds/fireball.ogg", false);
        AssetPool.addSound("assets/sounds/flagpole.ogg", false);
        AssetPool.addSound("assets/sounds/gameover.ogg", false);
        AssetPool.addSound("assets/sounds/invincible.ogg", false);
        AssetPool.addSound("assets/sounds/jump.ogg", false);
        AssetPool.addSound("assets/sounds/jump-super.ogg", false);
        AssetPool.addSound("assets/sounds/pipe.ogg", false);
        AssetPool.addSound("assets/sounds/power_up.ogg", false);
        AssetPool.addSound("assets/sounds/powerup_appears.ogg", false);
        AssetPool.addSound("assets/sounds/stage_clear.ogg", false);

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
            if(ImGui.beginTabItem("Solid Blocks")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 0; i < spritesTiles.size(); i++) {
                    if (i > 1 && i < 6) continue;
                    if (i >= 58 && i < 64) continue;
                    if (i >= 84 && i < 96) continue;
                    if (i >= 102 && i < 105) continue;
                    if (i >= 125 && i < 129) continue;
                    if (i >= 168) continue;
                    Sprite sprite = spritesTiles.getSprite(i);
                    float spriteWidth = sprite.getWidth() / 2;
                    float spriteHeight = sprite.getHeight() / 2;
                    int id = sprite.getTexId();
                    Vector2f[] texCoords = sprite.getTexCoords();

                    ImGui.pushID(i);
                    if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                        GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                        RigidBody2D rb = new RigidBody2D();
                        rb.setBodyType(BodyType.Static);
                        object.addComponent(rb);
                        Box2DCollider b2d = new Box2DCollider();
                        b2d.setHalfSize(new Vector2f(0.25f,0.25f));
                        object.addComponent(b2d);
                        object.addComponent(new Ground());
                        if (i < 15) {
                            object.addComponent(new BreakableBrick());
                        }

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

            if (ImGui.beginTabItem("Decoration Blocks")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 0; i < spritesTiles.size(); i++) {
                    if (i < 1) continue;
                    if (i >= 6 && i < 58) continue;
                    if (i >= 58 && i < 61) continue;
                    if (i >= 64 && i < 84) continue;
                    if (i >= 96 && i < 102) continue;
                    if (i >= 105 && i < 125) continue;
                    if (i >= 129 && i < 168) continue;
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
                int uid = 0;
                Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/spritesheets/p1_spritesheet.png");
                Sprite sprite = playerSprites.getSprite(1);
                float spriteWidth = sprite.getWidth() / 2;
                float spriteHeight = sprite.getHeight() / 2;
                int id = sprite.getTexId();
                Vector2f[] texCoords = sprite.getTexCoords();

                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateJumper();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();

                Spritesheet interactiveTiles = AssetPool.getSpritesheet("assets/images/spritesheets/tiles_spritesheet.png");
                sprite = interactiveTiles.getSprite(2);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateQuestionBlock();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();

                Spritesheet enemySprites = AssetPool.getSpritesheet("assets/images/spritesheets/enemies_spritesheet.png");
                sprite = enemySprites.getSprite(13);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateGoomba();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();

                sprite = enemySprites.getSprite(16);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateSnail();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();

                sprite = spritesTiles.getSprite(60);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateDoor(Direction.Up);
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();
                sprite = spritesTiles.getSprite(61);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();

                Spritesheet items = AssetPool.getSpritesheet("assets/images/spritesheets/items_spritesheet.png");
                sprite = items.getSprite(20);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateFlagtop();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();
                ImGui.sameLine();

                sprite = items.getSprite(12);
                id = sprite.getTexId();
                texCoords = sprite.getTexCoords();
                ImGui.pushID(uid++);
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateFlagPole();
                    levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                }
                ImGui.popID();

                ImGui.endTabItem();
            }
            if(ImGui.beginTabItem("Sounds")) {
                Collection<Sound> sounds = AssetPool.getAllSounds();
                for (Sound sound : sounds) {
                    File tmp = new File(sound.getFilepath());
                    if (ImGui.button(tmp.getName())) {
                        if (!sound.isPlaying()) {
                            sound.play();
                        } else {
                            sound.stop();
                        }
                    }

                    if (ImGui.getContentRegionAvailX() > 100) {
                        ImGui.sameLine();
                    }
                }

                ImGui.endTabItem();
            }

            ImGui.endTabBar();
        }
        ImGui.end();
    }
}
