package com.cloudamite.pacman.game;

import java.util.List;

public class ObjectLocation {
    private final Maze.Position mazePosition;
    private final Coordinates coordinates;

    protected ObjectLocation(Maze.Position mazePosition, Coordinates coordinates) {
        this.mazePosition = mazePosition;
        this.coordinates = coordinates;
    }

    public static ObjectLocation coordinates(Coordinates coordinates) {
        return new ObjectLocation(null, coordinates);
    }

    public static ObjectLocation mazePosition(Maze.Position pos) {
        return new ObjectLocation(pos, pos.toScreenCoordinates());
    }

    public boolean mazePositionMissing() {
        return mazePosition == null;
    }

    public ObjectLocation toDirection(Direction dir) {
        var newPos = mazePosition.toDirection(dir);
        if (newPos == null) return null;
        return new ObjectLocation(newPos, coordinates);
    }

    public LinearMotion linearMotionTo(ObjectLocation to, float speed) {
        return new LinearMotion(coordinates, to.mazePosition.toScreenCoordinates(), speed);
    }

    public ObjectLocation withNewCoordinates(Coordinates coordinates) {
        return new ObjectLocation(mazePosition, coordinates);
    }

    public ObjectLocation withNewMazePosition(Maze.Position position) {
        return new ObjectLocation(position, coordinates);
    }

    public float distanceTo(Coordinates coordinates) {
        return this.coordinates.distanceTo(coordinates);
    }

    public Coordinates coordinates() {
        return this.coordinates;
    }

    public Maze.Position mazePosition() {
        return this.mazePosition;
    }

    public List<Maze.Position> shortestPathTo(Maze.Position to) {
        return mazePosition.shortestPathTo(to);
    }
}
