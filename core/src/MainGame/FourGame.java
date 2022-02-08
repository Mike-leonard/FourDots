package MainGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.AssetLoader;
import screens.SplashScreen;

public class FourGame extends Game {

    private ActionResolver actionresolver;

    private Sprite sprite;

    public FourGame(ActionResolver actionresolver) {
        this.actionresolver = actionresolver;
    }

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this, actionresolver));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
