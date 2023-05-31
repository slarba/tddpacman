package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GhostMotionTests {
    private GameState gameState;
    private Ghost ghost;
    private Pacman pacman;
    private Maze maze;

    @BeforeEach
    void setup() {
        gameState = new GameState().randomSeed(31337L);
        maze = new Maze(
                "XXXXXXXX",
                "X     XX",
                "X X X  X",
                "XXX XXXX",
                "X      X",
                "X XXXX X",
                "X X    X",
                "XXXXXXXX"
        ).mazeCellDimensions(32, 32);
        pacman = new Pacman(gameState)
                .atMazePosition(maze.position(3, 1));
        var limage = new AnimatedImage(new TestImage("L"));
        var rimage = new AnimatedImage(new TestImage("R"));
        var uimage = new AnimatedImage(new TestImage("U"));
        var dimage = new AnimatedImage(new TestImage("D"));
        var scaredImage = new AnimatedImage(new TestImage("A"));
        ghost = new Ghost(gameState)
                .speed(32)
                .withImages(limage, rimage, uimage, dimage, scaredImage)
                .atMazePosition(maze.position(6, 5));
    }

    @Test void shouldMoveSmoothlyWhenAggressive() {
        ghost.unscare();
        advanceTime(0.2f);
        var renderer = render();
        assertEquals("[(177.00,160.00)]", renderer.renderedCoordinates());
    }

    @Test void shouldMoveSmoothlyWhenScared() {
        ghost.scare();
        advanceTime(0.2f);
        var renderer = render();
        assertEquals("[(177.00,160.00)]", renderer.renderedCoordinates());
    }

    @Test
    void shouldCatchPacmanInMaze() {
        ghost.unscare();
        advanceTime(25f);
        var renderer = render();
        assertEquals("[(96.00,32.00)]", renderer.renderedCoordinates());
    }

    @Test
    void shouldFaceRightWhenTravelingRight() {
        pacman.atMazePosition(maze.position(4,6));
        ghost.atMazePosition(maze.position(1,6));
        ghost.unscare();
        advanceTime(0.5f);
        assertEquals("R", render().renderedFrame());
    }

    @Test
    void shouldFaceDownWhenTravelingDown() {
        ghost.atMazePosition(maze.position(3,6));
        pacman.atMazePosition(maze.position(3,3));
        ghost.unscare();
        advanceTime(0.5f);
        assertEquals("D", render().renderedFrame());
    }

    @Test
    void shouldFaceUpWhenTravelingUp() {
        pacman.atMazePosition(maze.position(3,6));
        ghost.atMazePosition(maze.position(1,1));
        ghost.unscare();
        advanceTime(0.5f);
        assertEquals("U", render().renderedFrame());
    }

    @Test
    void shouldEvadePacmanIfScared() {
        ghost.scare();
        advanceTime(25f);
        var renderer = render();
        assertEquals("A", renderer.renderedFrame());
        assertEquals("[(32.00,32.00)]", renderer.renderedCoordinates());
    }

    @Test
    void shouldFaceLeftWhenMovingToLeft() {
        pacman.atMazePosition(maze.position(1,6));
        ghost.atMazePosition(maze.position(3,6));
        ghost.unscare();
        advanceTime(0.5f);
        assertEquals("L", render().renderedFrame());
    }

    @Test
    void shouldHaveEvadingStateAsDefaultWhenGameStartsAndThenSeek() {
        ghost.startSeekingPacman();
        advanceTime(10f);
        var renderer = render();
        assertEquals("[(32.00,32.00)]", renderer.renderedCoordinates());
        advanceTime(15f);
        renderer = render();
        assertEquals("[(96.00,32.00)]", renderer.renderedCoordinates());
    }

    private TestRenderer render() {
        var renderer = new TestRenderer();
        gameState.render(renderer);
        return renderer;
    }

    private void advanceTime(float secs) {
        while(secs>0) {
            float inc = Math.min(secs, 0.5f);
            gameState.advance(inc);
            secs -= inc;
        }
    }
}
