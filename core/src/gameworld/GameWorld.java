package gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import MainGame.ActionResolver;
import MainGame.FourGame;
import configuration.Configuration;
import configuration.Settings;
import gameobjects.Background;
import gameobjects.Ball;
import gameobjects.Core;
import gameobjects.GameObject;
import gameobjects.HUD;
import gameobjects.Menu;
import helpers.AssetLoader;
import helpers.FlatColors;
import ui.MuteButton;


public class GameWorld {

    public final float w;
    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public FourGame game;
    public GameWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    //VARIABLES
    private GameState gameState;
    private int score;

    //GAMEOBJECTS
    private Background background, top;
    private HUD hud;
    private Core core;
    private Ball ball;
    private Menu menu;
    private MuteButton muteButton;

    public GameWorld(FourGame game, ActionResolver actionResolver, float gameWidth,
                     float gameHeight, float worldWidth, float worldHeight) {

        this.gameWidth = gameWidth;
        this.w = gameHeight / 100;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;

        if (AssetLoader.getAds()) {
            actionResolver.viewAd(false);
        }

        gameState = GameState.MENU;
        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.background);
        resetGame();
        menu.start();
        top = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.square);
        top.fadeOut(.4f, 0f);

        muteButton = new MuteButton(gameWidth / 2, gameHeight - 40 - ((202 * 80 / 256) / 2),
                80,
                202 * 80 / 256,
                AssetLoader.soundButton, AssetLoader.muteButton, world.parseColor(Settings.SOUND_BUTTON_COLOR, 1f));
        muteButton.fadeIn(.9f, .8f, .5f);
        muteButton.setColor(FlatColors.BLACK);
        checkIfMusicWasPlaying();

        if (AssetLoader.getAds()) {
            world.actionResolver.viewAd(false);
        } else {
            world.actionResolver.viewAd(true);
        }
    }

    private void checkIfMusicWasPlaying() {
        if (AssetLoader.getVolume()) {
            AssetLoader.music.setLooping(true);
            AssetLoader.music.play();
            AssetLoader.music.setVolume(Settings.MUSIC_VOLUME);
            AssetLoader.setVolume(true);
        }
        if (AssetLoader.music.isPlaying()) {
            world.muteButton.isPressed = false;
        } else {
            world.muteButton.isPressed = true;
        }
    }

    public void update(float delta) {
        hud.update(delta);
        core.update(delta);
        ball.update(delta);
        menu.update(delta);
        top.update(delta);
        muteButton.update(delta);
        muteButton.getSprite().setPosition(muteButton.getSprite().getX(), hud.getPosition().y + 20);
    }

    public void render(SpriteBatch batcher, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        background.render(batcher, shapeRenderer);
        core.render(batcher, shapeRenderer);
        ball.render(batcher, shapeRenderer);
        hud.render(batcher, shapeRenderer, fontShader);
        menu.render(batcher, shapeRenderer, fontShader);
        muteButton.draw(batcher);
        top.render(batcher, shapeRenderer);


        if (Configuration.DEBUG) {
            batcher.setShader(fontShader);
            batcher.setShader(null);
        }
    }

    public void finishGame() {

        saveScoreLogic();
        gameState = GameState.MENU;
        hud.finish();
        core.finish();
        ball.finish();
        resetMenu();
        menu.start();


        //checkAchievements();

    }

    private void saveScoreLogic() {
        AssetLoader.addGamesPlayed();
        int gamesPlayed = AssetLoader.getGamesPlayed();

        // GAMES PLAYED ACHIEVEMENTS!
        actionResolver.submitScore(score);
        actionResolver.submitGamesPlayed(gamesPlayed);
        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
        }
        checkAchievements();
    }

    private void checkAchievements() {
        if (actionResolver.isSignedIn()) {
            if (score >= 5) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_5_P);
            if (score >= 10) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_P);
            if (score >= 25) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_P);
            if (score >= 50) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_P);
            if (score >= 100) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_P);
            if (score >= 200) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_P);

            int gamesPlayed = AssetLoader.getGamesPlayed();
            // GAMES PLAYED
            if (gamesPlayed >= 10)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_GP);
            if (gamesPlayed >= 25)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_GP);
            if (gamesPlayed >= 50)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_GP);
            if (gamesPlayed >= 100)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_GP);
            if (gamesPlayed >= 200)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_GP);


        }
    }

    public void startGame() {
        score = 0;
        gameState = GameState.RUNNING;
    }


    public GameCam getCamera() {
        return camera;
    }

    public int getScore() {
        return score;
    }


    public void addScore(int i) {
        score += i;
        hud.getScoreText().setText(Configuration.SCORE_TEXT + score);
        if (score > AssetLoader.getHighScore()) {
            hud.getBestText().setText(Configuration.BEST_TEXT + score);
        }
    }

    public static Color parseColor(String hex, float alpha) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = alpha;
        return color;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return gameState == GameState.GAMEOVER;
    }

    public boolean isMenu() {
        return gameState == GameState.MENU;
    }

    public FourGame getGame() {
        return game;
    }


    public void resetGame() {
        score = 0;
        core = new Core(world, gameWidth / 2 - Settings.CORE_SIZE / 2,
                gameHeight / 2 - Settings.CORE_SIZE / 2 + 100, Settings.CORE_SIZE,
                Settings.CORE_SIZE,
                AssetLoader.dot, FlatColors.LIGHT_BLACK,
                GameObject.Shape.CIRCLE);
        hud = new HUD(world, 0, world.gameHeight, gameWidth,
                Settings.HUD_SIZE, AssetLoader.square,
                world.parseColor(Settings.BACK_RECTANGLE_HUD_COLOR, 1f),
                GameObject.Shape.RECTANGLE);
        resetBall();
        resetMenu();
    }

    public void resetMenu() {
        menu = new Menu(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, FlatColors.WHITE,
                GameObject.Shape.RECTANGLE);
    }

    public void resetBall() {
        score = 0;
        ball = new Ball(world, gameWidth / 2 - (Settings.BALL_SIZE / 2), gameHeight + 100,
                Settings.BALL_SIZE, Settings.BALL_SIZE,
                AssetLoader.dot, FlatColors.RED, GameObject.Shape.CIRCLE);
    }

    public Ball getBall() {
        return ball;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Core getCore() {
        return core;
    }

    public HUD getHUD() {
        return hud;
    }

    public Menu getMenu() {
        return menu;
    }

    public MuteButton getMuteButton() {
        return muteButton;
    }


    public void setScore(int score) {
        this.score = score;
        hud.getScoreText().setText(Configuration.SCORE_TEXT + score);
        if (score > AssetLoader.getHighScore()) {
            hud.getBestText().setText(Configuration.BEST_TEXT + score);
        }
    }
}
