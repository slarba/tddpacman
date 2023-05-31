package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollisionDispatchTests {
    private GameState gameState;
    private Pacman pacman;

    @BeforeEach
    void setup() {
        gameState = new GameState();
        pacman = new Pacman(gameState);
    }

    @Test
    void shouldDispatchCollisionsWithPowerPill() {
        CollidableObject collidableObject = new PowerPill(gameState);
        pacman.collidesWith(collidableObject);
        assertEquals("000200", gameState.currentScore());
    }

    @Test
    void shouldDispatchCollisionsWithPill() {
        CollidableObject collidableObject = new Pill(gameState);
        pacman.collidesWith(collidableObject);
        assertEquals("000100", gameState.currentScore());
    }

    @Test
    void shouldDispatchCollisionsWithGhost() {
        var gameObject = new Ghost(gameState);
        gameObject.unscare();
        pacman.collidesWith(gameObject);
        assertEquals("2", gameState.currentLives());
    }

    @Test
    void shouldBeErrorIfCollidesWithItself() {
        assertThrows(IllegalStateException.class, () -> pacman.collidesWith(pacman));
    }
}
