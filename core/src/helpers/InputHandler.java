package helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import configuration.Configuration;
import gameworld.GameWorld;
import ui.MenuButton;

public class InputHandler implements InputProcessor {

    private GameWorld world;
    private float scaleFactorX;
    private float scaleFactorY;
    private Rectangle rectangle;
    private ArrayList<MenuButton> menuButtons;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        rectangle = new Rectangle(0, 0, 200, 200);
        menuButtons = world.getMenu().getMenuButtons();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.R) {
            //world.startGame();
            world.resetGame();
        } else if (keycode == Input.Keys.F) {
        } else if (keycode == Input.Keys.D) {
            if (Configuration.DEBUG) Configuration.DEBUG = false;
            else Configuration.DEBUG = true;
        } else if (keycode == Input.Keys.RIGHT) {
            world.getCore().rightClick();
        } else if (keycode == Input.Keys.LEFT) {
            world.getCore().leftClick();
        }
        //Gdx.app.log("GameState: ", world.getGameState().toString());
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (world.isMenu()) {
            for (int i = 0; i < world.getMenu().getMenuButtons().size(); i++) {
                if (world.getMenu().getMenuButtons().get(i).isTouchDown(screenX, screenY)) {
                }

            }
        } else if (world.isRunning()) {
            world.getMuteButton().isTouchDown(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        //Gdx.app.log("Clicked!!!","");
        if (world.isRunning()) {
            if (screenY < world.gameHeight - 200) {
                if (screenX < world.gameWidth / 2) {
                    world.getCore().leftClick();
                } else {
                    world.getCore().rightClick();
                }
            } else {
                //world.getMuteButton().isTouchUp(screenX,screenY);
            }
        } else if (world.isMenu()) {
            if (world.getMenu().getMenuButtons().get(0).isTouchUp(screenX, screenY)) {
                world.getMenu().startPlayButton();
                //JUST TESTING OUT TODO:
                if (AssetLoader.getAds()) {
                    world.actionResolver.viewAd(false);
                } else {
                    world.actionResolver.viewAd(true);
                }
            } else if (world.getMenu().getMenuButtons().get(2).isTouchUp(screenX, screenY)) {
                world.actionResolver.showScores();
            } else if (world.getMenu().getMenuButtons().get(3).isTouchUp(screenX, screenY)) {
                world.actionResolver.shareGame(Configuration.SHARE_MESSAGE);
            } else if (world.getMenu().getMenuButtons().get(1).isTouchUp(screenX, screenY)) {
                world.actionResolver.showAchievement();
            } else if (Configuration.IAP_ON) {
                if (world.getMenu().getMenuButtons().get(4).isTouchUp(screenX, screenY)) {
                    world.actionResolver.iapClick();
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (world.gameHeight - screenY / scaleFactorY);
    }
}
