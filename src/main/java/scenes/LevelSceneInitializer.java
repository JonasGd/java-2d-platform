package scenes;

import components.*;
import engine.Direction;
import engine.GameObject;
import engine.Prefabs;
import engine.Sound;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import physics2D.components.Box2DCollider;
import physics2D.components.RigidBody2D;
import physics2D.enums.BodyType;
import util.AssetPool;

import java.io.File;
import java.util.Collection;

public class LevelSceneInitializer extends SceneInitializer {

    public LevelSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/spritesheets/p1_spritesheet.png");
        Spritesheet spritesTiles = AssetPool.getSpritesheet("assets/images/spritesheets/tiles_spritesheet.png");

        GameObject cameraObject = scene.createGameObject("GameCamera");
        cameraObject.addComponent(new GameCamera(scene.camera()));
        cameraObject.start();
        scene.addGameObjectToScene(cameraObject);
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

        AssetPool.getSound("assets/sounds/main-theme.ogg").play();


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

    }
}
