package com.cloudamite.pacman;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cloudamite.pacman.game.Coordinates;
import com.cloudamite.pacman.game.Image;
import com.cloudamite.pacman.game.Renderer;

public class GdxRenderer implements Renderer {
    private final SpriteBatch spriteBatch;
    private final Coordinates origin = Coordinates.xy(65,60);
    private final BitmapFont font;

    public GdxRenderer(BitmapFont font) {
        spriteBatch = new SpriteBatch();
        this.font = font;
    }

    public void begin() {
        spriteBatch.begin();
    }

    public void end() {
        spriteBatch.end();
    }

    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }

    @Override
    public void renderImage(Image image, Coordinates at) {
        var img = (GdxImage) image;
        img.draw(at.plus(origin), spriteBatch);
    }

    @Override
    public void renderText(String text, Coordinates at) {
        font.setColor(Color.WHITE);
        at.plus(origin).map((x,y) -> font.draw(spriteBatch, text, x, y));
    }
}
