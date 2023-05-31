package com.cloudamite.pacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimationTests {
    private AnimatedImage image;
    private TestRenderer renderer;

    @BeforeEach
    void setup() {
        image = new AnimatedImage(1, new TestImage("1"), new TestImage("2"));
        renderer = new TestRenderer();
    }

    @Test
    void shouldRenderFirstFrameWhenNoTimeHasPassed() {
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("1", renderer.renderedFrame());
    }

    @Test
    void shouldRenderFrameAtCorrectCoordinates() {
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("(10.00,10.00)", renderer.lastRenderedFrameCoordinates());
    }

    @Test
    void shouldStillRenderFirstFrameAfterSomePassesButNotEnoughToSwapFrame() {
        image.advance(0.5f);
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("1", renderer.renderedFrame());
    }

    @Test
    void shouldRenderSecondFrameIfSufficientTimePasses() {
        image.advance(1.5f);
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("2", renderer.renderedFrame());
    }

    @Test
    void shouldWrapAround() {
        image.advance(2.0f);
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("1", renderer.renderedFrame());
    }

    @Test void shouldStopFrameProgressionIfStopped() {
        image.advance(0.5f);
        image.stop();
        image.advance(1.0f);
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("1", renderer.renderedFrame());
    }

    @Test void shouldResumeFrameProgressionIfStoppedAndStarted() {
        image.advance(0.5f);
        image.stop();
        image.advance(1.0f);
        image.start();
        image.advance(1.0f);
        image.render(renderer, Coordinates.xy(10, 10));
        assertEquals("2", renderer.renderedFrame());
    }
}
