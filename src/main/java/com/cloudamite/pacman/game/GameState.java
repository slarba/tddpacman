package com.cloudamite.pacman.game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final List<Ghost> ghosts = new ArrayList<>();
    private final List<Pill> pills = new ArrayList<>();
    private final List<PowerPill> powerPills = new ArrayList<>();
    private int score;
    private int lives = 3;
    private Timer ghostScareTimer = Timer.NULL;
    private Pacman pacman;
    private long randomSeed = 0;
    private Timer killAnimationTimer = Timer.NULL;

    public String currentScore() {
        return String.format("%06d", score);
    }

    public void pillEaten(Pill pill) {
        pills.remove(pill);
        score += 100;
    }

    public void powerPillEaten(PowerPill powerPill) {
        powerPills.remove(powerPill);
        ghosts.forEach(Ghost::scare);
        ghostScareTimer = new Timer(10).atExpiration(() -> ghosts.forEach(Ghost::unscare));
        score += 200;
    }

    public void pacmanKilled() {
        var origGhosts = new ArrayList<>(ghosts);
        killAnimationTimer = new Timer(11*0.34f).atExpiration(() -> {
            ghosts.addAll(origGhosts);
            ghosts.forEach(Ghost::resetPosition);
            pacman.unkill();
        });
        pacman.kill();
        ghosts.clear();
        lives--;
    }

    public void ghostEaten(Ghost ghost)
    {
        ghost.speed(180);
        score += 500;
    }

    public String currentLives() {
        return Integer.toString(lives);
    }

    public boolean gameOver() {
        return lives == 0;
    }

    public void registerGhost(Ghost ghost) {
        ghosts.add(ghost);
    }

    public void advance(float dt) {
        ghostScareTimer.advance(dt);
        killAnimationTimer.advance(dt);
        ghosts.forEach(g -> g.advance(dt));
        pacman.advance(dt);
    }

    public void renderPills(Renderer renderer) {
        pills.forEach(p -> p.render(renderer));
        powerPills.forEach(p -> p.render(renderer));
    }

    public void renderGhosts(Renderer renderer) {
        ghosts.forEach(p -> p.render(renderer));
    }

    public void addPill(Pill pill) {
        pills.add(pill);
    }

    public void addPowerPill(PowerPill pill) {
        powerPills.add(pill);
    }

    public void render(Renderer renderer) {
        renderPills(renderer);
        renderGhosts(renderer);
        if (pacman != null) pacman.render(renderer);
        renderScore(renderer);
        renderLives(renderer);
    }

    private void renderScore(Renderer renderer) {
        renderer.renderText(currentScore(), Coordinates.xy(0, 24*32));
    }

    private void renderLives(Renderer renderer) {
        renderer.renderText(currentLives(), Coordinates.xy(10*32, 24*32));
    }

    @SuppressWarnings("all")
    public void handleScreenCollisions() {
        // enhanced for loop or forEach cannot be used here since handlers modify the lists, resulting
        // ConcurrentModificationException
        for (int i = 0; i < pills.size(); i++) pacman.checkAndHandleScreenCollisionWith(pills.get(i));
        for (int i = 0; i < powerPills.size(); i++) pacman.checkAndHandleScreenCollisionWith(powerPills.get(i));
        for (int i = 0; i < ghosts.size(); i++) pacman.checkAndHandleScreenCollisionWith(ghosts.get(i));
    }

    public void registerPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public Maze.Position pacmanPosition() {
        return pacman.mazePosition();
    }

    public Maze.Position randomEvadingPosition(float radius) {
        long seed = randomSeed == 0 ? System.currentTimeMillis() : randomSeed;
        return pacmanPosition().randomPositionOutsideRadius(seed, radius);
    }

    public GameState randomSeed(long seed) {
        this.randomSeed = seed;
        return this;
    }
}
