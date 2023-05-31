package com.cloudamite.pacman.game;

import java.util.List;

public class EvasiveBehavior implements GhostBehavior {
    private final MotionChangedDirectionListener listener;
    private final GameState gameState;
    private float speed = 75;
    private LinearMotion motion;
    private List<Maze.Position> path;
    private int currentSegment;

    public EvasiveBehavior(GameState gameState, MotionChangedDirectionListener listener) {
        this.gameState = gameState;
        this.listener = listener;
    }

    public ObjectLocation start(ObjectLocation location) {
        if (location.mazePositionMissing()) return location;
        var evadePos = gameState.randomEvadingPosition(7);
        path = location.shortestPathTo(evadePos);
        if (path.size() < 2) return location;
        currentSegment = 0;
        return nextSegment(location);
    }

    private ObjectLocation nextSegment(ObjectLocation location) {
        if (allSegmentsHandled()) return start(location);
        var from = path.get(currentSegment);
        var to = path.get(currentSegment + 1);
        listener.directionChanged(from.directionTo(to));
        var newPos = location.withNewMazePosition(to);
        motion = location.linearMotionTo(newPos, speed);
        currentSegment++;
        return newPos;
    }

    private boolean allSegmentsHandled() {
        return currentSegment > path.size() - 2;
    }

    public ObjectLocation advance(ObjectLocation location, float dt) {
        if (motion == null) return location;
        var newLoc = location.withNewCoordinates(motion.advanceTime(dt));
        if (motion.isFinished()) return nextSegment(newLoc);
        return newLoc;
    }

    public void speed(float speed) {
        this.speed = speed;
    }
}
