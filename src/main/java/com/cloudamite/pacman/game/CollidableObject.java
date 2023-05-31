package com.cloudamite.pacman.game;

public interface CollidableObject {
    void dispatchCollision();

    boolean collidesOnScreenSpace(Coordinates coordinates, float collisionRadius);
}
