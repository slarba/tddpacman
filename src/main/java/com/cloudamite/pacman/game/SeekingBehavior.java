package com.cloudamite.pacman.game;

public class SeekingBehavior implements GhostBehavior {
    private final MotionChangedDirectionListener listener;
    private final GameState gameState;
    private float speed = 75;
    private LinearMotion motion;

    public SeekingBehavior(GameState gameState, MotionChangedDirectionListener listener) {
        this.gameState = gameState;
        this.listener = listener;
    }

    public ObjectLocation start(ObjectLocation location) {
        if (location.mazePositionMissing()) return location;
        // compute path
        var path = location.shortestPathTo(gameState.pacmanPosition());
        if (path.size() < 2) return location;
        listener.directionChanged(path.get(0).directionTo(path.get(1)));
        var newPos = location.withNewMazePosition(path.get(1));
        motion = location.linearMotionTo(newPos, speed);
        return newPos;
    }

    public ObjectLocation advance(ObjectLocation location, float dt) {
        if (motion == null) return location;
        var newLoc = location.withNewCoordinates(motion.advanceTime(dt));
        if (motion.isFinished()) return start(newLoc);
        return newLoc;
    }

    public void speed(float speed) {
        this.speed = speed;
    }
}
