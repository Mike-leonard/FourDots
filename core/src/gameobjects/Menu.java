package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Configuration;
import configuration.Settings;
import gameworld.GameState;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;
import ui.MenuButton;
import ui.Text;


public class Menu extends GameObject {
    public Text text, bestText, gamesPlayedText, scoreText;
    public ArrayList<MenuButton> menubuttons = new ArrayList<MenuButton>();
    private GameObject circle, rectangle;
    private Sprite title;

    public Menu(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        text = new Text(world, 0, world.gameHeight / 2 + 250 + 100 + 45 + 100,
                world.gameWidth, 150, AssetLoader.square, FlatColors.WHITE, Configuration.GAME_NAME,
                AssetLoader.fontXL, world.parseColor(Settings.TITLE_TEXT_COLOR, 1f), 10,
                Align.center);
        gamesPlayedText = new Text(world, 0, world.gameHeight / 2 + 250 + 100 + 30,
                world.gameWidth, 100, AssetLoader.square, FlatColors.WHITE,
                Configuration.GAMES_PLAYED_TEXT + AssetLoader.getGamesPlayed(), AssetLoader.fontS,
                world.parseColor(Settings.GAMEPLAY_TEXT_MENU_COLOR, 1f), 30, Align.center);
        bestText = new Text(world, 0, world.gameHeight / 2 + 100 - 150, world.gameWidth, 150,
                AssetLoader.square, FlatColors.WHITE,
                Configuration.HIGH_SCORE_TEXT + AssetLoader.getHighScore(),
                AssetLoader.fontB, world.parseColor(Settings.HIGHSCORE_TEXT_MENU_COLOR, 1f), 20,
                Align.center);
        scoreText = new Text(world, 0, world.gameHeight / 2 + 100, world.gameWidth, 150,
                AssetLoader.square, FlatColors.WHITE,
                Configuration.SCORE_TEXT_MENU + world.getScore(), AssetLoader.fontB,
                world.parseColor(Settings.SCORE_TEXT_MENU_COLOR, 1f), 20, Align.center);

        MenuButton playButton = new MenuButton(world,
                world.gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                world.gameHeight / 2 - 250 - (Settings.PLAY_BUTTON_SIZE) + 100,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.PLAY_BUTTON_COLOR, 1f),
                Shape.RECTANGLE,
                AssetLoader.playButtonUp);
        MenuButton leaderboardsButton = new MenuButton(world,
                world.gameWidth / 2 - ((Settings.BUTTON_SIZE * 2 + 30 + 15)),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.RANK_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.rankButtonUp);
        MenuButton achievementButton = new MenuButton(world,
                world.gameWidth / 2 - (15 + Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ACHIEVEMENT_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.achieveButtonUp);
        MenuButton shareButton = new MenuButton(world,
                world.gameWidth / 2 + (15),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.SHARE_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.shareButtonUp);
        MenuButton rateButton = new MenuButton(world,
                world.gameWidth / 2 + (15 + 30 + Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 100,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ADS_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.adsUp);

        menubuttons.add(playButton);
        menubuttons.add(achievementButton);
        menubuttons.add(leaderboardsButton);
        menubuttons.add(shareButton);
        if (Configuration.IAP_ON) menubuttons.add(rateButton);
        else {
            achievementButton.setPosition(
                    achievementButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    achievementButton.getPosition().y);
            leaderboardsButton.setPosition(
                    leaderboardsButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    leaderboardsButton.getPosition().y);
            shareButton.setPosition(
                    shareButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    shareButton.getPosition().y);
        }

        if (Settings.SHOW_BACK_CIRCLE) {
            circle = new GameObject(world,
                    world.gameWidth / 2 - (Settings.MENU_BACK_CIRCLE_SIZE / 2),
                    world.gameHeight / 2 - (Settings.MENU_BACK_CIRCLE_SIZE / 2),
                    Settings.MENU_BACK_CIRCLE_SIZE, Settings.MENU_BACK_CIRCLE_SIZE, AssetLoader.dot,
                    FlatColors.DARK_RED, Shape.CIRCLE);
            Tween.to(circle.getSprite(), SpriteAccessor.SCALE, 3f).target(1.2f)
                    .repeatYoyo(100000, .0f)
                    .ease(TweenEquations.easeInOutBounce).start(getManager());
        }

        rectangle = new GameObject(world, 0, world.gameHeight / 2 + 100 - 150, world.gameWidth, 300,
                AssetLoader.square, world.parseColor(Settings.BACK_RECTANGLE_MENU_COLOR, 1f),
                Shape.RECTANGLE);
        rectangle.getSprite().setAlpha(0);

        title = new Sprite(AssetLoader.title);
        title.setPosition(text.getPosition().x, text.getPosition().y);
        title.setSize(world.gameWidth,
                world.gameWidth / AssetLoader.title.getRegionWidth() * AssetLoader.title
                        .getRegionHeight());

        if (world.getScore() == AssetLoader.getHighScore() && world.getScore() != 0) {
            bestText.setFontColor(world.parseColor("#e3897f", 1f));
        }
    }

    public void start() {
        if (Settings.SHOW_BACK_CIRCLE)
            circle.fadeInFromTo(0, 0.1f, .8f, .1f);

        rectangle.fadeInFromTo(0, 0.8f, .8f, .1f);
        rectangle.effectY((rectangle.getPosition().y + world.gameHeight), rectangle.getPosition().y,
                .8f, .1f);
        world.setGameState(GameState.MENU);
        text.effectY((text.getPosition().y + world.gameHeight), text.getPosition().y, .8f, .1f);
        bestText.effectY((bestText.getPosition().y + world.gameHeight), bestText.getPosition().y,
                .8f, .1f);
        gamesPlayedText.effectY((gamesPlayedText.getPosition().y + world.gameHeight),
                gamesPlayedText.getPosition().y, .8f, .1f);
        scoreText.effectY((scoreText.getPosition().y + world.gameHeight),
                scoreText.getPosition().y, .8f, .1f);
        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(
                    menubuttons.get(i).getPosition().y - world.gameHeight,
                    menubuttons.get(i).getPosition().y, .8f, .1f);
        }
    }

    public void finish() {
        if (Settings.SHOW_BACK_CIRCLE)
            circle.fadeOutFrom(0.1f, 0.6f, .1f);
        rectangle.fadeOutFrom(0.8f, 0.6f, .1f);
        rectangle.effectY(rectangle.getPosition().y, rectangle.getPosition().y + world.gameHeight,
                .6f, .1f);
        text.effectY(text.getPosition().y, text.getPosition().y + world.gameHeight, .6f, .1f);
        bestText.effectY(bestText.getPosition().y, bestText.getPosition().y + world.gameHeight, .6f,
                .1f);
        gamesPlayedText.effectY(gamesPlayedText.getPosition().y,
                gamesPlayedText.getPosition().y + world.gameHeight, .6f,
                .1f);
        scoreText.effectY(scoreText.getPosition().y, scoreText.getPosition().y + world.gameHeight,
                .6f, .1f);
        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y,
                    menubuttons.get(i).getPosition().y - world.gameHeight, .6f, .1f);
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        text.update(delta);
        bestText.update(delta);
        gamesPlayedText.update(delta);
        scoreText.update(delta);
        rectangle.update(delta);
        title.setPosition(text.getPosition().x, text.getPosition().y);
        if (Settings.SHOW_BACK_CIRCLE)
            circle.update(delta);
        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).update(delta);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        //super.render(batch, shapeRenderer);
        if (Settings.SHOW_BACK_CIRCLE)
            circle.render(batch, shapeRenderer);
        rectangle.render(batch, shapeRenderer);
        if (Settings.USE_TITLE_TEXTURE) {
            title.draw(batch);
        } else {
            text.render(batch, shapeRenderer, fontShader);
        }
        bestText.render(batch, shapeRenderer, fontShader);
        scoreText.render(batch, shapeRenderer, fontShader);
        gamesPlayedText.render(batch, shapeRenderer, fontShader);
        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).render(batch, shapeRenderer);
        }
    }

    public void startPlayButton() {
        finish();
        world.getCore().start();
        world.getHUD().start();
        world.setScore(0);
        world.resetBall();
        world.setGameState(GameState.RUNNING);
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menubuttons;
    }
}
