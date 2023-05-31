package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PacmanDirectionDependentImageTests {
    private Pacman pacman;

    @BeforeEach
    void setup() {
        var gameState = new GameState();
        pacman = new Pacman(gameState)
                .withImages(image("LEFT"), image("RIGHT"), image("UP"), image("DOWN"),
                        image("DEAD"));
    }

    @Test
    void shouldNotRenderIfNoImagesSet() {
        var gameState = new GameState();
        pacman = new Pacman(gameState);
        assertNull(render().renderedFrame());
    }

    @Test
    void shouldHaveUpImageWhenTravelingUp() {
        pacman.move(Direction.UP);
        assertEquals("UP", render().renderedFrame());
    }

    @Test
    void shouldHaveDownImageWhenTravelingDown() {
        pacman.move(Direction.DOWN);
        assertEquals("DOWN", render().renderedFrame());
    }

    @Test
    void shouldHaveLeftImageWhenTravelingLeft() {
        pacman.move(Direction.LEFT);
        assertEquals("LEFT", render().renderedFrame());
    }

    @Test
    void shouldHaveRightImageWhenTravelingRight() {
        pacman.move(Direction.RIGHT);
        assertEquals("RIGHT", render().renderedFrame());
    }

    @Test
    void shouldHaveDeathAnimationWhenDied() {
        pacman.kill();
        assertEquals("DEAD", render().renderedFrame());
    }

    @Test
    void shouldFaceRightWhenNotYetMoved() {
        assertEquals("RIGHT", render().renderedFrame());
    }

    private TestRenderer render() {
        var renderer = new TestRenderer();
        pacman.render(renderer);
        return renderer;
    }

    private AnimatedImage image(String name) {
        return new AnimatedImage(new TestImage(name));
    }
}
