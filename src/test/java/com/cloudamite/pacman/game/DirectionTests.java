package com.cloudamite.pacman.game;

import org.junit.jupiter.api.Test;

import static com.cloudamite.pacman.game.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTests {
    @Test
    void shouldRecognizeOppositeDirectionsCorrectly() {
        assertTrue(RIGHT.oppositeTo(LEFT));
        assertTrue(LEFT.oppositeTo(RIGHT));
        assertTrue(UP.oppositeTo(DOWN));
        assertTrue(DOWN.oppositeTo(UP));

        assertFalse(RIGHT.oppositeTo(UP));
        assertFalse(RIGHT.oppositeTo(DOWN));
        assertFalse(RIGHT.oppositeTo(RIGHT));

        assertFalse(LEFT.oppositeTo(UP));
        assertFalse(LEFT.oppositeTo(DOWN));
        assertFalse(LEFT.oppositeTo(LEFT));

        assertFalse(UP.oppositeTo(LEFT));
        assertFalse(UP.oppositeTo(RIGHT));
        assertFalse(UP.oppositeTo(UP));

        assertFalse(DOWN.oppositeTo(LEFT));
        assertFalse(DOWN.oppositeTo(RIGHT));
        assertFalse(DOWN.oppositeTo(DOWN));
    }

    @Test
    void shouldGiveDirectionalOffsets() {
        assertEquals(-1, LEFT.xoffset());
        assertEquals(1, RIGHT.xoffset());
        assertEquals(0, UP.xoffset());
        assertEquals(0, DOWN.xoffset());

        assertEquals(0, LEFT.yoffset());
        assertEquals(0, RIGHT.yoffset());
        assertEquals(1, UP.yoffset());
        assertEquals(-1, DOWN.yoffset());
    }
}
