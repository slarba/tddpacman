package com.cloudamite.pacman.game;

public class PowerPill extends GameObject<PowerPill> {
    private final GameState gameState;
    private Image image = Image.NULL;

    public PowerPill(GameState gameState) {
        this.gameState = gameState;
        gameState.addPowerPill(this);
    }

    @Override
    public void dispatchCollision() {
        gameState.powerPillEaten(this);
    }

    public PowerPill withImage(Image image) {
        this.image = image;
        return this;
    }

    public void render(Renderer renderer) {
        image.render(renderer, location.coordinates());
    }
}
