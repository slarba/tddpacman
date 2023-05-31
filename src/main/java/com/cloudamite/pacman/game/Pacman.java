package com.cloudamite.pacman.game;

import java.util.Map;

public class Pacman extends GameObject<Pacman> implements MotionBehavior.MotionListener {
    private final MotionBehavior motionBehavior = new MotionBehavior(this);
    private Map<Direction, AnimatedImage> directionImages = Map.of();
    private AnimatedImage currentImage = AnimatedImage.NULL;
    private AnimatedImage deathImage = AnimatedImage.NULL;
    private boolean killed;

    @SuppressWarnings("unused")
    public Pacman(GameState gameState) {
        gameState.registerPacman(this);
    }

    public void collidesWith(CollidableObject collidableObject) {
        collidableObject.dispatchCollision();
    }

    public void checkAndHandleScreenCollisionWith(CollidableObject obj) {
        if (obj.collidesOnScreenSpace(location.coordinates(), collisionRadius)) {
            collidesWith(obj);
        }
    }

    public void move(Direction dir) {
        if(killed) return;
        chooseDirectionImage(dir);
        currentImage.start();
        location = motionBehavior.move(location, dir);
    }

    private void chooseDirectionImage(Direction dir) {
        currentImage = directionImages.getOrDefault(dir, AnimatedImage.NULL);
    }

    public void render(Renderer renderer) {
        currentImage.render(renderer, location.coordinates());
    }

    public Pacman withImages(AnimatedImage left, AnimatedImage right, AnimatedImage up, AnimatedImage down, AnimatedImage death) {
        directionImages = Map.of(Direction.LEFT, left,
                Direction.RIGHT, right,
                Direction.UP, up,
                Direction.DOWN, down);
        this.deathImage = death;
        currentImage = right;
        return this;
    }

    public void advance(float dt) {
        location = motionBehavior.advance(location, dt);
        currentImage.advance(dt);
    }

    public Pacman defaultSpeed(float speed) {
        motionBehavior.speed(speed);
        return this;
    }

    public Maze.Position mazePosition() {
        return location.mazePosition();
    }

    public void kill() {
        killed = true;
        motionBehavior.stop();
        currentImage = deathImage;
    }

    public void unkill() {
        killed = false;
        resetPosition();
        chooseDirectionImage(Direction.RIGHT);
    }

    @Override
    public void motionStopped() {
        currentImage.stop();
    }
}
