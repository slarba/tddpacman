package com.cloudamite.pacman.game;

public class TestGameObjectFactory {
    private final GameState gameState;
    private final TestImage pillImage = new TestImage(".");
    private final TestImage  powerPillImage = new TestImage("o");
    private final AnimatedImage pacmanImage = new AnimatedImage(new TestImage("C"));
    private final AnimatedImage ghostImage = new AnimatedImage(new TestImage("A"));
    private final AnimatedImage scaredGhostImage = new AnimatedImage(new TestImage("U"));
    private final AnimatedImage pacmanDeadImage = new AnimatedImage(new TestImage("-"));

    public TestGameObjectFactory(GameState gameState) {
        this.gameState = gameState;
    }

    public Pill pill() {
        return new Pill(gameState)
                .withCollisionRadius(5)
                .withImage(pillImage);
    }

    public PowerPill powerPill() {
        return  new PowerPill(gameState)
                .withCollisionRadius(5)
                .withImage(powerPillImage);
    }

    public Pacman pacman() {
        return new Pacman(gameState)
                .withCollisionRadius(5)
                .withImages(pacmanImage, pacmanImage, pacmanImage, pacmanImage, pacmanDeadImage);
    }

    public Ghost ghost() {
        return new Ghost(gameState)
                .withCollisionRadius(5)
                .withImages(ghostImage, ghostImage, ghostImage, ghostImage, scaredGhostImage);
    }
}
