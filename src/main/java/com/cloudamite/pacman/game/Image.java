package com.cloudamite.pacman.game;

public interface Image {
    Image NULL = new NullImage();

    void render(Renderer renderer, Coordinates at);
}
