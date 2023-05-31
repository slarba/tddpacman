package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpatialCollisionTests {
    private GameState gameState;
    private Pacman pacman;
    private Pill pill;

    @BeforeEach
    void setup() {
        gameState = new GameState();
        pacman = new Pacman(gameState).withCollisionRadius(5);
        pill = new Pill(gameState).withCollisionRadius(5);
    }

    @Test
    void shouldCollideIfCollisionShapesIntersect() {
        pacman.atCoordinates(Coordinates.xy(10, 10))
                .checkAndHandleScreenCollisionWith(pill.atCoordinates(Coordinates.xy(5, 10)));
        assertEquals("000100", gameState.currentScore());
    }

    @Test
    void shouldNotCollideWhenShapesDontIntersect() {
        pacman.atCoordinates(Coordinates.xy(10, 10))
                .checkAndHandleScreenCollisionWith(pill.atCoordinates(Coordinates.xy(25, 10)));
        assertEquals("000000", gameState.currentScore());
    }
}
