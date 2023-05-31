package com.cloudamite.pacman.game;

public abstract class GameObject<T> implements CollidableObject {
    protected float collisionRadius = 0;
    protected ObjectLocation location = ObjectLocation.coordinates(Coordinates.ORIGIN);
    private ObjectLocation originalLocation = location;

    @Override
    public boolean collidesOnScreenSpace(Coordinates coordinates, float collisionRadius) {
        return this.location.distanceTo(coordinates) < (this.collisionRadius + collisionRadius);
    }

    @SuppressWarnings("unchecked")
    public T withCollisionRadius(float radius) {
        this.collisionRadius = radius;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T atCoordinates(Coordinates coordinates) {
        this.location = ObjectLocation.coordinates(coordinates);
        this.originalLocation = location;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T atMazePosition(Maze.Position pos) {
        this.location = ObjectLocation.mazePosition(pos);
        this.originalLocation = location;
        return (T) this;
    }

    public void resetPosition() {
        this.location = originalLocation;
    }

    @Override
    public void dispatchCollision() {
        throw new IllegalStateException("collision with itself");
    }
}
