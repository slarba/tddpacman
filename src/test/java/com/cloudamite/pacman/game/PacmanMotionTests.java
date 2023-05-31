package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacmanMotionTests {
    private Pacman pacman;

    @BeforeEach
    void setup() {
        var maze = new Maze(
                "XXXXXX",
                "X    X",
                "X    X",
                "XXXXXX"
        ).mazeCellDimensions(32, 32);
        var gameState = new GameState();
        var image = new AnimatedImage(testImage());
        pacman = new Pacman(gameState)
                .withImages(image, image, image, image, AnimatedImage.NULL)
                .defaultSpeed(32)
                .atMazePosition(maze.position(1, 1));
    }

    @Test
    void shouldStayInPlaceByDefault() {
        pacman.advance(.5f);
        assertEquals("(32.00,32.00)", render().lastRenderedFrameCoordinates());
    }

    @Test
    void shouldNotMoveWhenCommandedToBlockedDirection() {
        pacman.move(Direction.LEFT);
        pacman.advance(0.5f);
        assertEquals("(32.00,32.00)", render().lastRenderedFrameCoordinates());
    }

    @Test
    void shouldMoveWhenCommandedToFreeDirection() {
        pacman.move(Direction.RIGHT);
        pacman.advance(0.5f);
        assertEquals("(48.00,32.00)", render().lastRenderedFrameCoordinates());
    }

    @Test
    void shouldContinueMovingToFreeDirection() {
        pacman.move(Direction.RIGHT);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        assertEquals("(80.00,32.00)", render().lastRenderedFrameCoordinates());
    }

    @Test
    void shouldStopMovingIfCollidesWithWall() {
        pacman.move(Direction.RIGHT);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        pacman.advance(0.5f);
        assertEquals("(128.00,32.00)", render().lastRenderedFrameCoordinates());
    }

    @Test
    void shouldReverseMotionImmediatelyBetweenCellsLeftRight() {
        pacman.move(Direction.RIGHT);
        pacman.advance(0.5f);
        pacman.move(Direction.LEFT);
        pacman.advance(0.4f);
        pacman.advance(0.6f);
        assertEquals("(32.00,32.00)", render().lastRenderedFrameCoordinates());
    }

    private TestRenderer render() {
        var renderer = new TestRenderer();
        pacman.render(renderer);
        return renderer;
    }

    private Image testImage() {
        return new TestImage("C");
    }
}
