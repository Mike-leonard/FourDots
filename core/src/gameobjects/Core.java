package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Configuration;
import configuration.Settings;
import gameworld.GameState;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.Value;


public class Core extends GameObject {

    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Color> ballColors = new ArrayList<Color>();
    private ArrayList<TextureRegion> ballTextures = new ArrayList<TextureRegion>();
    private int numOfDots = Settings.NUM_OF_DOTS;
    private GameObject effectCircle;
    private Value angle = new Value();
    private TweenCallback cbClick;
    private Tween rotationTween;
    private TweenCallback cbAds;
    private Value second = new Value();


    private enum CoreState {IDLE, TRANSITION}

    private CoreState coreState;

    public Core(final GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        coreState = CoreState.IDLE;

        ballColors.add(world.parseColor(Settings.DOT_1_COLOR, 1f));
        ballColors.add(world.parseColor(Settings.DOT_2_COLOR, 1f));
        ballColors.add(world.parseColor(Settings.DOT_3_COLOR, 1f));
        ballColors.add(world.parseColor(Settings.DOT_4_COLOR, 1f));
        ballColors.add(world.parseColor(Settings.DOT_5_COLOR, 1f));
        ballColors.add(world.parseColor(Settings.DOT_6_COLOR, 1f));

        ballTextures.add(AssetLoader.dot1);
        ballTextures.add(AssetLoader.dot2);
        ballTextures.add(AssetLoader.dot3);
        ballTextures.add(AssetLoader.dot4);
        ballTextures.add(AssetLoader.dot5);
        ballTextures.add(AssetLoader.dot6);

        angle.setValue(0);
        for (int i = 0; i < numOfDots; i++) {
            Dot dot = new Dot(world, x, y, Settings.DOTS_SIZE, Settings.DOTS_SIZE,
                    ballTextures.get(i),
                    ballColors.get(i), Shape.CIRCLE, (360 / numOfDots) * i, i + 1);
            dots.add(dot);
        }

        effectCircle = new GameObject(world, x, y, Settings.CORE_SIZE, Settings.CORE_SIZE,
                AssetLoader.dot, ballColors.get(0), Shape.CIRCLE);
        effectCircle.getSprite().setAlpha(0f);

        cbClick = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                coreState = CoreState.IDLE;
            }
        };
        cbAds = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.actionResolver.showOrLoadInterstital();
            }
        };
        rotationTween = Tween.to(angle, -1, 4).target(720).delay(0).repeatYoyo(100000, .1f)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public void start() {
        rotationTween.kill();
        effectYStart(getPosition().y, Settings.CORE_Y_VALUE, .8f, .1f);
        rotateTo(360, .8f, .0f);
    }

    public void finish() {
        effectYStart(getPosition().y, world.gameHeight / 2 - getSprite().getHeight() / 2 + 100, .8f,
                .1f);
        rotateTo(360, .8f, .0f);
        rotationTween = Tween.to(angle, -1, 4).target(720).delay(.81f).repeatYoyo(100000, .0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());

        if (!AssetLoader.getAds()) {
            second.setValue(0);
            if (Math.random() < Configuration.AD_FREQUENCY) {
                Tween.to(second, -1, .8f).target(1).delay(.15f).setCallbackTriggers(
                        TweenCallback.COMPLETE)
                        .setCallback(cbAds).ease(TweenEquations.easeInOutSine).start(getManager());
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).update(delta);
            dots.get(i).setAngle(angle.getValue() + (360 / numOfDots) * i);
        }
        if (angle.getValue() == 360) angle.setValue(0);
        if (angle.getValue() == -360) angle.setValue(0);

        effectCircle.update(delta);
        effectCircle.setPosition(getPosition());
        collisions();
    }

    private void collisions() {
        for (int i = 0; i < dots.size(); i++) {
            if (world.getGameState() == GameState.RUNNING && Intersector
                    .overlaps(world.getBall().getCircle(), dots.get(i).getCircle()) && !world
                    .getBall().scored) {
                if (dots.get(i).getType() == world.getBall().getType()) {
                    world.getBall().scaleAndReset();
                    world.addScore(1);
                    effectCircle.setColor(ballColors.get(i));
                    collisionEffect();
                    AssetLoader.success.play();
                } else {
                    world.finishGame();
                    AssetLoader.end.play();
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        effectCircle.render(batch, shapeRenderer);
        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).render(batch, shapeRenderer);
        }
    }

    public void leftClick() {
        if (coreState == coreState.IDLE) {
            rotateToTransition(angle.getValue() + (360 / numOfDots), Settings.ROTATION_DURATION,
                    0f);
            AssetLoader.click.play();
        }
    }

    public void rightClick() {
        if (coreState == coreState.IDLE) {
            rotateToTransition(angle.getValue() - (360 / numOfDots), Settings.ROTATION_DURATION,
                    0f);
            AssetLoader.click.play();
        }
    }

    public ArrayList<Color> getBallColors() {
        return ballColors;
    }

    public void collisionEffect() {
        effectCircle.fadeOutFrom(.2f, .6f, .0f);
        effectCircle.scale(0, 10, .6f, .0f);
    }

    public void rotateTo(float ang, float duration, float delay) {
        Tween.to(angle, -1, duration).target(ang).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public void rotateToTransition(float ang, float duration, float delay) {
        coreState = CoreState.TRANSITION;
        Tween.to(angle, -1, duration).target(ang).delay(delay).setCallback(cbClick)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public ArrayList<TextureRegion> getBallTextures() {
        return ballTextures;
    }
}


