package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeathTests {
    private GameState gameState;
    private Pacman pacman;
    private Ghost ghost;

    @BeforeEach
    void setup() {
        gameState = new GameState();
        pacman = new Pacman(gameState);
        ghost = new Ghost(gameState);
    }

    @Test
    void shouldHaveThreeLivesByDefault() {
        assertEquals("3", gameState.currentLives());
    }

    @Test
    void shouldLoseALifeWhenCollidesWithAggressiveGhost() {
        ghost.unscare();
        pacman.collidesWith(ghost);
        assertEquals("2", gameState.currentLives());
    }

    @Test
    void shouldNotBeGameOverWhenLivesLeft() {
        assertFalse(gameState.gameOver());
    }

    @Test
    void shouldBeGameOverWhenAllLivesLost() {
        ghost.unscare();
        pacman.collidesWith(ghost);
        pacman.collidesWith(ghost);
        pacman.collidesWith(ghost);
        assertTrue(gameState.gameOver());
    }
}
