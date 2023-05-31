package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTests {
    private Maze maze;

    @BeforeEach
    void setup() {
        maze = new Maze(
                "XXX XXX",
                "X ....X",
                "X..   X",
                " .     ",
                "X.   oX",
                "X.   oX",
                "XXX XXX"
        );
    }

    @Test
    void shouldValidateMaze() {
        assertThrows(IllegalArgumentException.class, Maze::new);
        assertThrows(IllegalArgumentException.class, () -> new Maze(""));
        assertThrows(IllegalArgumentException.class, () -> new Maze("XX", "XXX"));
    }

    @Test
    void shouldValidatePosition() {
        assertThrows(IllegalArgumentException.class, () -> maze.position(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.position(7, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.position(0, -1));
        assertThrows(IllegalArgumentException.class, () -> maze.position(0, 7));
    }

    @Test
    void shouldGiveNewPositionIfRouteIsNotBlocked() {
        var pos = maze.position(1, 1);
        assertEquals("[1,2]", pos.toDirection(Direction.UP).toString());
        assertEquals("[2,1]", pos.toDirection(Direction.RIGHT).toString());
    }

    @Test
    void shouldNotGiveNewPositionIfRouteIsBlocked() {
        var pos = maze.position(1, 1);
        assertNull(pos.toDirection(Direction.LEFT));
        assertNull(pos.toDirection(Direction.DOWN));
    }

    @Test
    void shouldWrapCoordinates() {
        var pos = maze.position(3, 0);
        assertEquals("[3,6]", pos.toDirection(Direction.DOWN).toString());
        pos = maze.position(0, 3);
        assertEquals("[6,3]", pos.toDirection(Direction.LEFT).toString());
        pos = maze.position(6, 3);
        assertEquals("[0,3]", pos.toDirection(Direction.RIGHT).toString());
        pos = maze.position(3, 6);
        assertEquals("[3,0]", pos.toDirection(Direction.UP).toString());
    }

    @Test
    void shouldFindShortestPath() {
        var pos = maze.position(1, 1);
        var result = pos.shortestPathTo(maze.position(5, 5));
        assertEquals("[[1,1], [1,2], [2,2], [3,2], [4,2], [4,3], [4,4], [5,4], [5,5]]", result.toString());
    }

    @Test
    void shouldFindShortestPathWhereStartAndGoalAreEqual() {
        var pos = maze.position(1, 1);
        var result = pos.shortestPathTo(maze.position(1, 1));
        assertEquals("[[1,1]]", result.toString());
    }

    @Test
    void shouldFindShortestPathWhereStartAndGoalAreAdjacent() {
        var pos = maze.position(1, 1);
        var result = pos.shortestPathTo(maze.position(1, 2));
        assertEquals("[[1,1], [1,2]]", result.toString());
    }

    @Test
    void shouldNotBlockIfGoalPositionIsBlocked() {
        var pos = maze.position(1, 1);
        assertThrows(IllegalArgumentException.class, () -> pos.shortestPathTo(maze.position(0, 0)));
    }

    @Test
    void shouldIterateOverPillPositions() {
        final var result = new ArrayList<Coordinates>();
        maze.forEachPillPosition(result::add);
        assertEquals("[(1.00,1.00), (1.00,2.00), (1.00,3.00), (1.00,4.00), (2.00,4.00), (2.00,5.00), (3.00,5.00), (4.00,5.00), (5.00,5.00)]", result.toString());
    }

    @Test
    void shouldIterateOverPowerPillPositions() {
        final var result = new ArrayList<Coordinates>();
        maze.forEachPowerPillPosition(result::add);
        assertEquals("[(5.00,1.00), (5.00,2.00)]", result.toString());
    }

    @Test
    void shouldThrowIfAskingForDirectionToNonAdjacentPosition() {
        assertThrows(IllegalArgumentException.class, () -> maze.position(1, 1).directionTo(maze.position(5, 5)));
    }
}
