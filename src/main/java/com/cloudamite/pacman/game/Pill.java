package com.cloudamite.pacman.game;

public class Pill extends GameObject<Pill> {
    private final GameState gameState;
    private Image image = Image.NULL;

    public Pill(GameState gameState) {
        this.gameState = gameState;
        gameState.addPill(this);
    }

    @Override
    public void dispatchCollision() {
        gameState.pillEaten(this);
    }

    public Pill withImage(Image image) {
        this.image = image;
        return this;
    }

    public void render(Renderer renderer) {
        image.render(renderer, location.coordinates());
    }
}
