package com.cloudamite.pacman.game;

import java.util.Map;

public class Ghost extends GameObject<Ghost> implements MotionChangedDirectionListener {
    private final GameState gameState;
    private boolean scared;
    private GhostBehavior motionBehavior;
    private Map<Direction, AnimatedImage> directionImages = Map.of();
    private AnimatedImage currentImage = AnimatedImage.NULL;

    private AnimatedImage scaredImage = AnimatedImage.NULL;
    private AnimatedImage lastImage;

    private Timer behaviorSwitchTimer = Timer.NULL;

    public Ghost(GameState gameState) {
        this.gameState = gameState;
        this.motionBehavior = new SeekingBehavior(gameState, this);
        gameState.registerGhost(this);
    }

    public void scare() {
        lastImage = currentImage;
        currentImage = scaredImage;
        scared = true;
        motionBehavior = new EvasiveBehavior(gameState, this);
        motionBehavior.speed(75);
        location = motionBehavior.start(location);
    }

    public void unscare() {
        currentImage = lastImage;
        scared = false;
        motionBehavior = new SeekingBehavior(gameState, this);
        motionBehavior.speed(75);
        location = motionBehavior.start(location);
    }

    @Override
    public void dispatchCollision() {
        if (scared) {
            gameState.ghostEaten(this);
            return;
        }
        gameState.pacmanKilled();
    }

    public Ghost withImages(AnimatedImage left, AnimatedImage right, AnimatedImage up, AnimatedImage down, AnimatedImage scared) {
        directionImages = Map.of(Direction.LEFT, left,
                Direction.RIGHT, right,
                Direction.UP, up,
                Direction.DOWN, down);
        scaredImage = scared;
        currentImage = right;
        lastImage = right;
        return this;
    }

    public void render(Renderer renderer) {
        currentImage.render(renderer, location.coordinates());
    }

    public void startSeekingPacman() {
        this.motionBehavior = new EvasiveBehavior(gameState, this);
        location = motionBehavior.start(location);
        behaviorSwitchTimer = new Timer(12).atExpiration(() -> {
            this.motionBehavior = new SeekingBehavior(gameState, this);
            location = motionBehavior.start(location);
        });
    }

    public void advance(float dt) {
        behaviorSwitchTimer.advance(dt);
        currentImage.advance(dt);
        location = motionBehavior.advance(location, dt);
    }

    public Ghost speed(float speed) {
        motionBehavior.speed(speed);
        return this;
    }

    @Override
    public void resetPosition() {
        super.resetPosition();
        motionBehavior = new SeekingBehavior(gameState, this);
        motionBehavior.speed(75);
        location = motionBehavior.start(location);
    }

    @Override
    public void directionChanged(Direction dir) {
        if (!scared)
            currentImage = directionImages.getOrDefault(dir, AnimatedImage.NULL);
    }
}
