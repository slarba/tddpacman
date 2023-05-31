package com.cloudamite.pacman.game;

public interface Renderer {
    void renderImage(Image image, Coordinates at);
    void renderText(String text, Coordinates at);
}
