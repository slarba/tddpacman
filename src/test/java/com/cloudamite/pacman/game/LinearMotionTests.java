package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinearMotionTests {
    private LinearMotion motion;

    @BeforeEach
    void setup() {
        var from = Coordinates.xy(10, 10);
        var to = Coordinates.xy(20, 10);
        float speed = 1;
        motion = new LinearMotion(from, to, speed);
    }

    @Test
    void shouldNotBeFinishedAtTheStart() {
        assertFalse(motion.isFinished());
    }

    @Test
    void shouldBeAtTheStartingPositionAfterCreation() {
        assertEquals("(10.00,10.00)", motion.currentCoords().toString());
    }

    @Test
    void shouldAdvanceProperDistanceWhenTimeAdvances() {
        motion.advanceTime(1);
        assertFalse(motion.isFinished());
        assertEquals("(11.00,10.00)", motion.currentCoords().toString());
    }

    @Test
    void shouldStopAtTheEnd() {
        motion.advanceTime(20);
        assertEquals("(20.00,10.00)", motion.currentCoords().toString());
        assertTrue(motion.isFinished());
    }

    @Test
    void shouldReverse() {
        motion.advanceTime(5);
        motion.reverse();
        motion.advanceTime(2);
        assertEquals("(13.00,10.00)", motion.currentCoords().toString());
    }

    @Test
    void shouldTravelToStartWhenReversed() {
        motion.advanceTime(5);
        motion.reverse();
        motion.advanceTime(7);
        assertEquals("(10.00,10.00)", motion.currentCoords().toString());
    }

    @Test
    void shouldNotBeFinishedInTheBeginning() {
        assertFalse(motion.isFinished());
    }

    @Test
    void shouldNotBeFinishedInTheMiddle() {
        motion.advanceTime(1);
        assertFalse(motion.isFinished());
    }

    @Test
    void shouldBeFinishedAtTheEnd() {
        motion.advanceTime(20);
        assertTrue(motion.isFinished());
    }

    @Test
    void shouldBeFinishedAtTheBeginningAfterReversal() {
        motion.advanceTime(2);
        motion.reverse();
        motion.advanceTime(1);
        motion.advanceTime(2);
        assertTrue(motion.isFinished());
        assertEquals("(10.00,10.00)", motion.currentCoords().toString());
    }
}
