package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Settings;
import gameworld.GameWorld;
import tweens.SpriteAccessor;

/**
 * Created by ManuGil on 29/04/15.
 */
public class Ball extends GameObject {
    private TweenCallback cbReset;
    private int type;
    private float actualVel;
    public boolean scored = false;

    public Ball(final GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        setRandomType();
        cbReset = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                scored = false;
                //Gdx.app.log("Vel",actualVel+"");
                setScale(1);
                if (Settings.UP_AND_DOWN_SPAWNS) {
                    if (Math.random() < 0.5f) {
                        setPosition(getPosition().x, world.gameHeight + 100);
                        setVelocity(new Vector2(0, -Math.abs(actualVel)));
                    } else {
                        setPosition(getPosition().x, -100);
                        setVelocity(new Vector2(0, Math.abs(actualVel)));
                    }
                } else {
                    setPosition(getPosition().x, world.gameHeight + 100);
                    setVelocity(new Vector2(0, -Math.abs(actualVel)));
                }
                setRandomType();

            }


        };
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //Gdx.app.log("Velocity", getVelocity().y + "");
        if (getVelocity().y < Settings.BALL_MAX_SPEED) {
            setVelocity(new Vector2(0, Settings.BALL_MAX_SPEED));
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
    }

    public void scaleAndReset() {
        scored = true;
        actualVel = getVelocity().y;
        setVelocity(new Vector2());
        Tween.to(getSprite(), SpriteAccessor.SCALE, .1f).target(0).delay(0f)
                .ease(TweenEquations.easeInOutSine).setCallback(cbReset)
                .setCallbackTriggers(TweenCallback.COMPLETE).start(
                getManager());

    }

    private void setRandomType() {
        type = MathUtils.random(1, Settings.NUM_OF_DOTS);
        getSprite().setColor(world.getCore().getBallColors().get(type - 1));
        if (Settings.USE_TEXTURES) {
            getSprite().setRegion(world.getCore().getBallTextures().get(type - 1));
            getSprite().setColor(Color.WHITE);
        }
    }

    public int getType() {
        return type;
    }

    public void finish() {
        setVelocity(new Vector2());
        scaleZero(.3f, .0f);
    }
}
