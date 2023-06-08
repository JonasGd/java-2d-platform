package engine;

import components.AnimationState;
import components.PlayerController;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import components.StateMachine;
import org.joml.Vector2f;
import physics2D.components.PillboxCollider;
import physics2D.components.RigidBody2D;
import physics2D.enums.BodyType;
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
        Spritesheet bigPlayerSprites = AssetPool.getSpritesheet("assets/images/spritesheets/p2_spritesheet.png");
        Spritesheet firePlayerSprites = AssetPool.getSpritesheet("assets/images/spritesheets/p3_spritesheet.png");
        GameObject jumper = generateSpriteObject(playerSprites.getSprite(1), "Jumper", 0.25f, 0.25f);

        //Normal animations
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

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(playerSprites.getSprite(1), 0.2f);
        switchDirection.setLoop(false);

        AnimationState idle = new AnimationState();
        idle.title = "Idle";
        idle.addFrame(playerSprites.getSprite(4), 0.1f);
        idle.setLoop(false);

        AnimationState jump = new AnimationState();
        jump.title = "Jump";
        jump.addFrame(playerSprites.getSprite(3), 0.1f);
        jump.setLoop(false);

        // Big mario animations
        AnimationState bigRun = new AnimationState();
        bigRun.title = "BigRun";
        bigRun.addFrame(bigPlayerSprites.getSprite(5), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(6), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(7), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(8), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(9), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(10), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(11), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(12), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(13), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(14), defaultFrameTime);
        bigRun.addFrame(bigPlayerSprites.getSprite(15), defaultFrameTime);
        bigRun.setLoop(true);

        AnimationState bigSwitchDirection = new AnimationState();
        bigSwitchDirection.title = "Big Switch Direction";
        bigSwitchDirection.addFrame(bigPlayerSprites.getSprite(1), 0.1f);
        bigSwitchDirection.setLoop(false);

        AnimationState bigIdle = new AnimationState();
        bigIdle.title = "BigIdle";
        bigIdle.addFrame(bigPlayerSprites.getSprite(4), 0.1f);
        bigIdle.setLoop(false);

        AnimationState bigJump = new AnimationState();
        bigJump.title = "BigJump";
        bigJump.addFrame(bigPlayerSprites.getSprite(3), 0.1f);
        bigJump.setLoop(false);

        // Fire mario animations
        AnimationState fireRun = new AnimationState();
        fireRun.title = "FireRun";
        fireRun.addFrame(firePlayerSprites.getSprite(5), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(6), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(7), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(8), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(9), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(10), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(11), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(12), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(13), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(14), defaultFrameTime);
        fireRun.addFrame(firePlayerSprites.getSprite(15), defaultFrameTime);
        fireRun.setLoop(true);

        AnimationState fireSwitchDirection = new AnimationState();
        fireSwitchDirection.title = "Fire Switch Direction";
        fireSwitchDirection.addFrame(firePlayerSprites.getSprite(1), 0.1f);
        fireSwitchDirection.setLoop(false);

        AnimationState fireIdle = new AnimationState();
        fireIdle.title = "FireIdle";
        fireIdle.addFrame(firePlayerSprites.getSprite( 4), 0.1f);
        fireIdle.setLoop(false);

        AnimationState fireJump = new AnimationState();
        fireJump.title = "FireJump";
        fireJump.addFrame(firePlayerSprites.getSprite( 3), 0.1f);
        fireJump.setLoop(false);

        AnimationState die = new AnimationState();
        die.title = "Die";
        die.addFrame(playerSprites.getSprite(2), 0.1f);
        die.setLoop(false);


        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(run);
        stateMachine.addState(idle);
        stateMachine.addState(switchDirection);
        stateMachine.addState(jump);
        stateMachine.addState(die);

        stateMachine.addState(bigRun);
        stateMachine.addState(bigIdle);
        stateMachine.addState(bigSwitchDirection);
        stateMachine.addState(bigJump);

        stateMachine.addState(fireRun);
        stateMachine.addState(fireIdle);
        stateMachine.addState(fireSwitchDirection);
        stateMachine.addState(fireJump);

        stateMachine.setDefaultState(idle.title);
        stateMachine.addState(run.title, switchDirection.title, "switchDirection");
        stateMachine.addState(run.title, idle.title, "stopRunning");
        stateMachine.addState(run.title, jump.title, "jump");
        stateMachine.addState(switchDirection.title, idle.title, "stopRunning");
        stateMachine.addState(switchDirection.title, run.title, "startRunning");
        stateMachine.addState(switchDirection.title, jump.title, "jump");
        stateMachine.addState(idle.title, run.title, "startRunning");
        stateMachine.addState(idle.title, jump.title, "jump");
        stateMachine.addState(jump.title, idle.title, "stopJumping");

        stateMachine.addState(bigRun.title, bigSwitchDirection.title, "switchDirection");
        stateMachine.addState(bigRun.title, bigIdle.title, "stopRunning");
        stateMachine.addState(bigRun.title, bigJump.title, "jump");
        stateMachine.addState(bigSwitchDirection.title, bigIdle.title, "stopRunning");
        stateMachine.addState(bigSwitchDirection.title, bigRun.title, "startRunning");
        stateMachine.addState(bigSwitchDirection.title, bigJump.title, "jump");
        stateMachine.addState(bigIdle.title, bigRun.title, "startRunning");
        stateMachine.addState(bigIdle.title, bigJump.title, "jump");
        stateMachine.addState(bigJump.title, bigIdle.title, "stopJumping");

        stateMachine.addState(fireRun.title, fireSwitchDirection.title, "switchDirection");
        stateMachine.addState(fireRun.title, fireIdle.title, "stopRunning");
        stateMachine.addState(fireRun.title, fireJump.title, "jump");
        stateMachine.addState(fireSwitchDirection.title, fireIdle.title, "stopRunning");
        stateMachine.addState(fireSwitchDirection.title, fireRun.title, "startRunning");
        stateMachine.addState(fireSwitchDirection.title, fireJump.title, "jump");
        stateMachine.addState(fireIdle.title, fireRun.title, "startRunning");
        stateMachine.addState(fireIdle.title, fireJump.title, "jump");
        stateMachine.addState(fireJump.title, fireIdle.title, "stopJumping");

        stateMachine.addState(run.title, bigRun.title, "powerup");
        stateMachine.addState(idle.title, bigIdle.title, "powerup");
        stateMachine.addState(switchDirection.title, bigSwitchDirection.title, "powerup");
        stateMachine.addState(jump.title, bigJump.title, "powerup");
        stateMachine.addState(bigRun.title, fireRun.title, "powerup");
        stateMachine.addState(bigIdle.title, fireIdle.title, "powerup");
        stateMachine.addState(bigSwitchDirection.title, fireSwitchDirection.title, "powerup");
        stateMachine.addState(bigJump.title, fireJump.title, "powerup");

        stateMachine.addState(bigRun.title, run.title, "damage");
        stateMachine.addState(bigIdle.title, idle.title, "damage");
        stateMachine.addState(bigSwitchDirection.title, switchDirection.title, "damage");
        stateMachine.addState(bigJump.title, jump.title, "damage");
        stateMachine.addState(fireRun.title, bigRun.title, "damage");
        stateMachine.addState(fireIdle.title, bigIdle.title, "damage");
        stateMachine.addState(fireSwitchDirection.title, bigSwitchDirection.title, "damage");
        stateMachine.addState(fireJump.title, bigJump.title, "damage");

        stateMachine.addState(run.title, die.title, "die");
        stateMachine.addState(switchDirection.title, die.title, "die");
        stateMachine.addState(idle.title, die.title, "die");
        stateMachine.addState(jump.title, die.title, "die");
        stateMachine.addState(bigRun.title, run.title, "die");
        stateMachine.addState(bigSwitchDirection.title, switchDirection.title, "die");
        stateMachine.addState(bigIdle.title, idle.title, "die");
        stateMachine.addState(bigJump.title, jump.title, "die");
        stateMachine.addState(fireRun.title, bigRun.title, "die");
        stateMachine.addState(fireSwitchDirection.title, bigSwitchDirection.title, "die");
        stateMachine.addState(fireIdle.title, bigIdle.title, "die");
        stateMachine.addState(fireJump.title, bigJump.title, "die");
        jumper.addComponent(stateMachine);

        PillboxCollider pb = new PillboxCollider();
        pb.width = 0.39f;
        pb.height = 0.31f;
        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyType.Dynamic);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        rb.setMass(25.0f);

        jumper.addComponent(rb);
        jumper.addComponent(pb);
        jumper.addComponent(new PlayerController());

        return jumper;
    }
}
