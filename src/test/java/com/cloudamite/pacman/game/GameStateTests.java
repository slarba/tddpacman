package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateTests {
    private GameState gameState;
    private TestGameObjectFactory factory;
    private TestRenderer renderer;

    @BeforeEach void setup() {
        gameState = new GameState();
        factory = new TestGameObjectFactory(gameState);
        renderer = new TestRenderer();
    }

    @Test
    void shouldRenderState() {
        factory.pill().atCoordinates(Coordinates.xy(10, 10));
        factory.pill().atCoordinates(Coordinates.xy(20, 20));
        factory.powerPill().atCoordinates(Coordinates.xy(30, 30));
        factory.pacman().atCoordinates(Coordinates.xy(40, 40));
        factory.ghost().atCoordinates(Coordinates.xy(50, 50));
        gameState.render(renderer);
        assertEquals("..oAC", renderer.renderedFrame());
        assertEquals("[(10.00,10.00), (20.00,20.00), (30.00,30.00), (50.00,50.00), (40.00,40.00)]", renderer.renderedCoordinates());
    }

    @Test
    void shouldHandleCollisions_pacmanDies() {
        factory.pill().atCoordinates(Coordinates.xy(10, 10));
        factory.pill().atCoordinates(Coordinates.xy(20, 20));
        factory.powerPill().atCoordinates(Coordinates.xy(30, 30));
        factory.pacman().atCoordinates(Coordinates.xy(20, 20));
        factory.ghost().atCoordinates(Coordinates.xy(20, 20));
        gameState.handleScreenCollisions();
        gameState.render(renderer);
        // pill should have been eaten and pacman dead, ghosts should disappear
        assertEquals(".o-", renderer.renderedFrame());
        assertEquals("2", gameState.currentLives());
        assertEquals("[(10.00,10.00), (30.00,30.00), (20.00,20.00)]", renderer.renderedCoordinates());
    }

    @Test
    void shouldHandleCollisions_pacmanDiesAndGameResets() {
        factory.pill().atCoordinates(Coordinates.xy(10, 10));
        factory.pill().atCoordinates(Coordinates.xy(20, 20));
        factory.powerPill().atCoordinates(Coordinates.xy(30, 30));
        factory.pacman().atCoordinates(Coordinates.xy(20, 20));
        factory.ghost().atCoordinates(Coordinates.xy(20, 20));
        gameState.handleScreenCollisions();
        gameState.advance(25f);
        gameState.render(renderer);
        assertEquals(".oAC", renderer.renderedFrame());
        assertEquals("2", gameState.currentLives());
        assertEquals("[(10.00,10.00), (30.00,30.00), (20.00,20.00), (20.00,20.00)]", renderer.renderedCoordinates());
    }

    @Test
    void shouldHandleCollisions_ghostEvades() {
        factory.powerPill().atCoordinates(Coordinates.xy(30, 30));
        factory.pacman().atCoordinates(Coordinates.xy(30, 30));
        factory.ghost().atCoordinates(Coordinates.xy(50, 50));
        factory.ghost().atCoordinates(Coordinates.xy(50, 50));
        gameState.handleScreenCollisions();
        gameState.render(renderer);
        assertEquals("UUC", renderer.renderedFrame());
        assertEquals("3", gameState.currentLives());
        assertEquals("[(50.00,50.00), (50.00,50.00), (30.00,30.00)]", renderer.renderedCoordinates());
    }
}
