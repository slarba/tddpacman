package com.cloudamite.pacman.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoordinateInterpolationTests {
    @Test
    void shouldInterpolateBetweenTwoPoints() {
        var from = Coordinates.xy(10, 10);
        var to = Coordinates.xy(20, 20);
        assertEquals("(15.00,15.00)", from.linearInterpolate(to, 0.5f).toString());
    }

    @Test
    void shouldErrorIfExtrapolating() {
        var from = Coordinates.xy(10, 10);
        var to = Coordinates.xy(20, 20);
        assertThrows(IllegalArgumentException.class, () -> from.linearInterpolate(to, 1.5f));
        assertThrows(IllegalArgumentException.class, () -> from.linearInterpolate(to, -1f));
    }

    @Test
    void shouldMap() {
        Coordinates.xy(2, 3).map((x, y) -> {
            assertEquals(2, x);
            assertEquals(3, y);
        });
    }
}
