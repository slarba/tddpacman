package com.cloudamite.pacman.game;

import java.util.Locale;

public class Coordinates {
    public static final Coordinates ORIGIN = Coordinates.xy(0, 0);
    private final float x, y;

    private Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates xy(float x, float y) {
        return new Coordinates(x, y);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "(%.2f,%.2f)", x, y);
    }

    public float distanceTo(Coordinates to) {
        float dx = to.x - x;
        float dy = to.y - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public Coordinates linearInterpolate(Coordinates to, float percentageTravelled) {
        if (percentageTravelled < 0 || percentageTravelled > 1.0) {
            throw new IllegalArgumentException("must have 0 <= percentageTravelled <= 1");
        }
        float dx = to.x - x;
        float dy = to.y - y;
        return Coordinates.xy(x + dx * percentageTravelled, y + dy * percentageTravelled);
    }

    public void map(CoordMapper mapper) {
        mapper.map(x, y);
    }

    public Coordinates plus(Coordinates b) {
        return Coordinates.xy(x + b.x, y + b.y);
    }

    public interface CoordMapper {
        void map(float x, float y);
    }
}
