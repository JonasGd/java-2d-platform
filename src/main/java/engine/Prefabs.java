package engine;

import components.AnimationState;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import components.StateMachine;
import org.joml.Vector2f;
import util.AssetPool;

public class Prefabs {
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        return generateSpriteObject(sprite, "Sprite_Object_Gen", sizeX, sizeY);
    }

    public static GameObject generateSpriteObject(Sprite sprite, String name, float sizeX, float sizeY) {
        GameObject block = Window.getScene().createGameObject(name);
        block.transform.scale.x = sizeX;
        block.transform.scale.y = sizeY;
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static GameObject generateJumper() {
        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/spritesheets/p1_spritesheet.png");
        GameObject jumper = generateSpriteObject(playerSprites.getSprite(1), "Jumper", 0.25f, 0.25f);

        AnimationState run = new AnimationState();
        run.title = "Run";
        float defaultFrameTime = 0.1f;
        run.addFrame(playerSprites.getSprite(5), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(6), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(7), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(8), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(9), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(10), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(11), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(12), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(13), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(14), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(15), defaultFrameTime);
        run.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(run);
        stateMachine.setDefaultState(run.title);
        jumper.addComponent(stateMachine);

        return jumper;
    }
}
