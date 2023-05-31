package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerPillEatingTests {
    private GameState gameState;
    private Pacman pacman;
    private Ghost ghost1;
    private Ghost ghost2;
    private PowerPill powerPill;

    @BeforeEach
    void setup() {
        gameState = new GameState();
        pacman = new Pacman(gameState);
        ghost1 = new Ghost(gameState);
        ghost2 = new Ghost(gameState);
        powerPill = new PowerPill(gameState);
    }

    @Test
    void shouldScareAllGhostsWhenPowerPillIsEaten() {
        pacman.collidesWith(powerPill);
        pacman.collidesWith(ghost1);
        pacman.collidesWith(ghost2);
        assertEquals("3", gameState.currentLives());
    }

    @Test
    void shouldLeaveGhostsScaredAfter5Seconds() {
        pacman.collidesWith(powerPill);
        gameState.advance(5);
        pacman.collidesWith(ghost1);
        pacman.collidesWith(ghost2);
        assertEquals("3", gameState.currentLives());
    }

    @Test
    void shouldMakeGhostsAggressiveAgainAfter10Seconds() {
        pacman.collidesWith(powerPill);
        gameState.advance(15);
        pacman.collidesWith(ghost1);
        pacman.collidesWith(ghost2);
        assertEquals("1", gameState.currentLives());
    }
}
