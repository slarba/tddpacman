package com.cloudamite.pacman.game;

public class LinearMotion {
    private final Coordinates from;
    private final Coordinates to;
    private final float totalTime;
    private Coordinates currentCoords;
    private float currentTime;

    private float sign = 1;
    private boolean finished;

    public LinearMotion(Coordinates from, Coordinates to, float speed) {
        this.from = from;
        this.to = to;
        this.currentCoords = from;
        this.totalTime = from.distanceTo(to) / speed;
        this.currentTime = 0;
    }

    public Coordinates currentCoords() {
        return currentCoords;
    }

    public Coordinates advanceTime(float dt) {
        currentTime += dt * sign;
        if (currentTime < 0) {
            finished = true;
            currentTime = 0;
        }
        if (currentTime > totalTime) {
            finished = true;
            currentTime = totalTime;
        }
        float percentageTravelled = currentTime / totalTime;
        currentCoords = from.linearInterpolate(to, percentageTravelled);
        return currentCoords;
    }

    public void reverse() {
        sign *= -1;
    }

    public boolean isFinished() {
        return finished;
    }
}
