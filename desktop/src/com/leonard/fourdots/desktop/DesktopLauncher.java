package com.leonard.fourdots.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import MainGame.FourGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Four Dots";
        config.width= (int) (1080 / 3);
        config.height = (int) (1720 / 3);
        new LwjglApplication(new FourGame(new ActionResolverDesktop()), config);
    }
}
