package com.cloudamite.pacman.game;

import java.util.ArrayList;
import java.util.List;

class TestRenderer implements Renderer {
    private final List<Coordinates> renderedAt = new ArrayList<>();
    private String renderedFrame;

    public String renderedFrame() {
        return renderedFrame != null ? renderedFrame : null;
    }

    @Override
    public void renderImage(Image image, Coordinates at) {
        if (this.renderedFrame == null) this.renderedFrame = "";
        this.renderedFrame += image.toString();
        this.renderedAt.add(at);
    }

    @Override
    public void renderText(String text, Coordinates at) {
    }

    public String lastRenderedFrameCoordinates() {
        return renderedAt.get(renderedAt.size() - 1).toString();
    }

    public String renderedCoordinates() {
        return renderedAt.toString();
    }
}
