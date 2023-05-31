package com.cloudamite.pacman.game;

public class Timer {
    public static final Timer NULL = new Timer(0);
    private float delay;
    private Runnable action;

    public Timer(float delaySeconds) {
        this.delay = delaySeconds;
    }

    public Timer atExpiration(Runnable action) {
        this.action = action;
        return this;
    }

    public void advance(float dt) {
        if (action == null) return;
        delay -= dt;
        if (delay <= 0) {
            action.run();
            action = null;
        }
    }
}
