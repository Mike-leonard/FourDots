package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.Value;

/**
 * Created by ManuGil on 29/04/15.
 */
public class Dot extends GameObject {

    private float angle;
    private Value angleValue = new Value();
    private TweenCallback cbEffectAngle;
    private DotState dotState;
    private int type;


    private enum DotState {
        TRANSITION, IDLE
    }

    public Dot(GameWorld world, float x, float y, float width, float height,
               TextureRegion texture,
               Color color, Shape shape, float angle, int type) {
        super(world, x, y, width, height, texture, color, shape);
        this.angle = angle;
        this.type = type;
        dotState = DotState.IDLE;
        angleValue.setValue(angle);
        cbEffectAngle = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dotState = DotState.IDLE;
            }
        };
        if (Settings.USE_TEXTURES) {
            getSprite().setColor(Color.WHITE);
        } else {
            getSprite().setRegion(AssetLoader.dot);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //angle = angleValue.getValue();
        getCircle().setPosition(calculatePosition());
        getPosition().set(calculatePosition());
        getSprite().setPosition(getCircle().x - getCircle().radius,
                getCircle().y - getCircle().radius);
        getSprite().setRotation(angle);

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
    }

    private Vector2 calculatePosition() {
        float cx = world.getCore().getPosition().x + (Settings.CORE_SIZE / 2);
        float cy = world.getCore().getPosition().y + (Settings.CORE_SIZE / 2);
        return new Vector2(
                (float) (cx + (getCircle().radius + Settings.DISTANCE_CENTER_TO_DOT) * Math
                        .sin(Math.toRadians(-angle))),
                (float) (cy + (getCircle().radius + Settings.DISTANCE_CENTER_TO_DOT) * Math
                        .cos(Math.toRadians(-angle))));
    }

    public void effectAngle(float to, float duration, float delay) {
        if (dotState == DotState.IDLE) {
            dotState = DotState.TRANSITION;
            Tween.to(angleValue, -1, duration).target(to).delay(delay)
                    .ease(TweenEquations.easeInOutSine).setCallback(cbEffectAngle)
                    .setCallbackTriggers(
                            TweenCallback.COMPLETE).start(getManager());
        }
    }

    public void rotate(float to, float duration, float delay) {
        Tween.to(angleValue, -1, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).setCallback(cbEffectAngle)
                .setCallbackTriggers(
                        TweenCallback.COMPLETE).start(getManager());

    }

    public float getAngle() {
        return angle;
    }

    public int getType() {
        return type;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
