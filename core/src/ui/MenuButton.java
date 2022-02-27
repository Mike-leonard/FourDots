package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import configuration.Settings;
import gameobjects.GameObject;
import gameworld.GameWorld;
import tweens.Value;


public class MenuButton extends GameObject {

    private Color color;
    private Value time = new Value();
    private Sprite icon;

    public MenuButton(final GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture, Color color, Shape shape, TextureRegion buttonIcon) {
        super(world, x, y, width, height, texture, color, shape);
        this.color = color;

        icon = new Sprite(buttonIcon);
        icon.setPosition(getPosition().x, getPosition().y);
        icon.setSize(width, height);
        if (!Settings.REMOVE_CIRCLE_BUTTONS)
            icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
        if (isPressed) {
            //icon.setAlpha(.5f);
            getSprite().setAlpha(.5f);
        } else {
           // icon.setAlpha(1f);
            getSprite().setAlpha(1f);
        }
        icon.draw(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        icon.setPosition(getPosition().x, getPosition().y);
    }


}