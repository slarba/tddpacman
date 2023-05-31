package com.cloudamite.pacman.game;

public interface GhostBehavior {
    ObjectLocation advance(ObjectLocation location, float dt);

    ObjectLocation start(ObjectLocation location);

    void speed(float speed);
}
