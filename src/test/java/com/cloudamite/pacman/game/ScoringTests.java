package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoringTests {
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
    void shouldHaveInitialScoreOfZero() {
        assertEquals("000000", gameState.currentScore());
    }

    @Test
    void shouldGet100PointsWhenPacmanCollidesWithAPill() {
        var pill = new Pill(gameState);
        pacman.collidesWith(pill);
        assertEquals("000100", gameState.currentScore());
    }

    @Test
    void shouldGet200PointsWhenPacmanCollidesWithPowerPill() {
        var powerPill = new PowerPill(gameState);
        pacman.collidesWith(powerPill);
        assertEquals("000200", gameState.currentScore());
    }

    @Test
    void shouldGet500PointsWhenPacmanCollidesWithScaredGhost() {
        ghost.scare();
        pacman.collidesWith(ghost);
        assertEquals("000500", gameState.currentScore());
    }

    @Test
    void shouldNotScoreWhenPacmanCollidesWithAggressiveGhost() {
        ghost.unscare();
        pacman.collidesWith(ghost);
        assertEquals("000000", gameState.currentScore());
    }
}
