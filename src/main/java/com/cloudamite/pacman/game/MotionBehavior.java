package com.cloudamite.pacman.game;

public class MotionBehavior {
    private final MotionListener listener;
    private float speed = 1;
    private LinearMotion motion;
    private Direction requestedDirection;

    public interface MotionListener {
        void motionStopped();
    }

    public MotionBehavior(MotionListener listener) {
        this.listener = listener;
    }

    public ObjectLocation move(ObjectLocation old, Direction dir) {
        if (old.mazePositionMissing()) return old;
        var opposite = isOppositeToTravel(dir);
        requestedDirection = dir;
        if (motionInProgress() && !opposite) return old;
        return startMotion(old, requestedDirection);
    }

    private boolean motionInProgress() {
        return motion != null && !motion.isFinished();
    }

    private boolean isOppositeToTravel(Direction dir) {
        return requestedDirection != null && dir.oppositeTo(requestedDirection);
    }

    private ObjectLocation startMotion(ObjectLocation old, Direction dir) {
        var newLoc = old.toDirection(dir);
        if (newLoc == null) {
            motion = null;
            if(listener!=null) listener.motionStopped();
            return old;
        }
        motion = old.linearMotionTo(newLoc, speed);
        return newLoc;
    }

    public ObjectLocation advance(ObjectLocation old, float dt) {
        if (motion == null) return old;

        var loc = old.withNewCoordinates(motion.advanceTime(dt));
        if (!motion.isFinished()) return loc;

        loc = startMotion(loc, requestedDirection);
        if(motion==null) return loc;
        return loc.withNewCoordinates(motion.advanceTime(dt));
    }

    public void speed(float speed) {
        this.speed = speed;
    }

    public void stop() {
        motion = null;
    }
}
