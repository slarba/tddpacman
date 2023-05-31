package com.cloudamite.pacman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeRenderingTests {
    @Test
    void shouldRenderMaze() {
        var maze = new Maze(
                "XXXXX",
                "X   X",
                "X XX+",
                "X   X",
                "XXXXX"
        ).mazeCellDimensions(10, 10)
                .cellImage(new TestImage("X"));

        var renderer = new TestRenderer();
        maze.render(renderer, Coordinates.xy(10, 10));
        assertEquals("[(10.00,10.00), (20.00,10.00), (30.00,10.00), (40.00,10.00), (50.00,10.00)," +
                " (10.00,20.00), (50.00,20.00), (10.00,30.00), (30.00,30.00), (40.00,30.00)," +
                " (10.00,40.00), (50.00,40.00), (10.00,50.00), (20.00,50.00)," +
                " (30.00,50.00), (40.00,50.00), (50.00,50.00)]", renderer.renderedCoordinates());
    }
}
