package com.cloudamite.pacman.game;

record TestImage(String frame) implements Image {
    @Override
    public String toString() {
        return frame;
    }

    @Override
    public void render(Renderer renderer, Coordinates at) {
        renderer.renderImage(this, at);
    }
}
