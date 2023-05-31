package com.cloudamite.pacman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PillEatingTests {
    private static TestRenderer render(GameState gameState) {
        var renderer = new TestRenderer();
        gameState.renderPills(renderer);
        return renderer;
    }

    @Test
    void shouldRenderPillsIfNotEaten() {
        var gameState = new GameState();
        new Pill(gameState).withImage(new TestImage("a"));
        new Pill(gameState).withImage(new TestImage("b"));
        assertEquals("ab", render(gameState).renderedFrame());
    }

    @Test
    void shouldDisappearWhenPillIsEaten() {
        var gameState = new GameState();
        var pacman = new Pacman(gameState);
        var pill = new Pill(gameState).withImage(new TestImage("a"));
        new Pill(gameState).withImage(new TestImage("b"));
        pacman.collidesWith(pill);
        assertEquals("b", render(gameState).renderedFrame());
    }

    @Test
    void shouldRenderPowerPillsIfNotEaten() {
        var gameState = new GameState();
        new PowerPill(gameState).withImage(new TestImage("a"));
        new PowerPill(gameState).withImage(new TestImage("b"));
        assertEquals("ab", render(gameState).renderedFrame());
    }

    @Test
    void shouldDisappearWhenPowerPillIsEaten() {
        var gameState = new GameState();
        var pacman = new Pacman(gameState);
        var pill = new PowerPill(gameState).withImage(new TestImage("a"));
        new PowerPill(gameState).withImage(new TestImage("b"));
        pacman.collidesWith(pill);
        assertEquals("b", render(gameState).renderedFrame());
    }
}
