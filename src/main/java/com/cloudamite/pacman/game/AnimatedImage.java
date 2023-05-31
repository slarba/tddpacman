package com.cloudamite.pacman.game;

public class AnimatedImage implements Image {
    public static final AnimatedImage NULL = new AnimatedImage();
    private final Image[] frames;
    private final float frameDuration;
    private float currentTime = 0;
    private int currentFrame = 0;
    private boolean stopped;

    protected AnimatedImage() {
        this(1, Image.NULL);
    }

    public AnimatedImage(Image image) {
        this(1, image);
    }

    public AnimatedImage(float fps, Image... frames) {
        this.frames = frames;
        this.frameDuration = 1.0f / fps;
    }

    public void render(Renderer renderer, Coordinates at) {
        frames[currentFrame].render(renderer, at);
    }

    public void advance(float dt) {
        if(stopped) return;
        currentTime += dt;
        if (currentTime > frameDuration) {
            float nOfFrames = currentTime / frameDuration;
            currentFrame = (currentFrame + (int) nOfFrames) % frames.length;
            currentTime -= nOfFrames * frameDuration;
        }
    }

    public void stop() {
        stopped = true;
    }

    public void start() {
        stopped = false;
    }
}
