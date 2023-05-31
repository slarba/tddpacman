package com.cloudamite.pacman;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Launcher {
    public static void main(String[] args) {
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Pacman");
        config.setWindowedMode(1000, 900);
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setResizable(false);
        new Lwjgl3Application(new App(), config);
    }
}
