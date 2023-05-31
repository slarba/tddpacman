package com.cloudamite.pacman.game;

public class NullImage implements Image {
    @Override
    public void render(Renderer renderer, Coordinates at) {
        // do nothing
    }
}
