package com.cloudamite.pacman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimerTests {
    private int fired = 0;

    private void fired() {
        fired++;
    }

    @Test
    void shouldNotFireAfterInsufficientDelay() {
        var timer = new Timer(5).atExpiration(this::fired);
        timer.advance(2);
        assertEquals(0, fired);
    }

    @Test
    void shouldFireAfterSufficientDelay() {
        var timer = new Timer(5).atExpiration(this::fired);
        timer.advance(5);
        assertEquals(1, fired);
    }

    @Test
    void shouldNotFireTwice() {
        var timer = new Timer(5).atExpiration(this::fired);
        timer.advance(5);
        timer.advance(1);
        assertEquals(1, fired);
    }
}
