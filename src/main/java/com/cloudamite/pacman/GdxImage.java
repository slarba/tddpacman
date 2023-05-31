package com.cloudamite.pacman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cloudamite.pacman.game.Coordinates;
import com.cloudamite.pacman.game.Image;
import com.cloudamite.pacman.game.Renderer;

public class GdxImage implements Image {
    private final TextureRegion sprite;

    public GdxImage(TextureRegion sprite) {
        this.sprite = sprite;
    }

    @Override
    public void render(Renderer renderer, Coordinates at) {
        renderer.renderImage(this, at);
    }

    public void draw(Coordinates at, SpriteBatch spriteBatch) {
        at.map((x, y) -> spriteBatch.draw(sprite, x, y));
    }
}
