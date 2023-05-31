package com.cloudamite.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cloudamite.pacman.game.*;

import java.util.Arrays;

public class App extends ApplicationAdapter {
    private GdxRenderer renderer;
    private Texture sprites;
    private GameState game;
    private Pacman pacman;
    private Maze maze;
    private Texture cellImage;
    private TextureRegion[][] regions;

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        renderer.begin();
        maze.render(renderer, Coordinates.ORIGIN);
        game.render(renderer);
        renderer.end();

        handleInputs();
        step(Gdx.graphics.getDeltaTime());
    }

    public void step(float dt) {
        game.advance(dt);
        game.handleScreenCollisions();
    }

    private void handleInputs() {
        Direction direction = null;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) direction = Direction.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direction = Direction.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) direction = Direction.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) direction = Direction.DOWN;
        if (direction != null)
            pacman.move(direction);
    }

    private BitmapFont createFont(FileHandle classpath, int size) {
        var generator = new FreeTypeFontGenerator(classpath);
        var param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = size;
        var font = generator.generateFont(param);
        generator.dispose();
        return font;
    }

    @Override
    public void create() {
        var font = createFont(Gdx.files.classpath("emulogic.ttf"), 32);
        renderer = new GdxRenderer(font);
        sprites = new Texture(Gdx.files.classpath("sprites32.png"));
        regions = TextureRegion.split(sprites, 32, 32);
        cellImage = new Texture(Gdx.files.classpath("cell.png"));
        TextureRegion[][] cellRegions = TextureRegion.split(cellImage, 32, 32);

        maze = new Maze(
                "XXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "X.o..........X..........o.X",
                "X.XXXX.XXXXX.X.XXXXX.XXXX.X",
                "X.X++X.X+++X.X.X+++X.X++X.X",
                "X.XXXX.XXXXX.X.XXXXX.XXXX.X",
                "X.........................X",
                "X.XXXX.X.XXXXXXXXX.X.XXXX.X",
                "X......X.....X.....X......X",
                "XXXXXX.XXXXX X XXXXX.XXXXXX",
                "+++++X.X           X.X+++++",
                "XXXXXX.X XXXX XXXX X.XXXXXX",
                "      .  X       X  .      ",
                "XXXXXX.X XXXXXXXXX X.XXXXXX",
                "+++++X.X           X.X+++++",
                "XXXXXX.X XXXXXXXXX X.XXXXXX",
                "X............X............X",
                "X.XXXX.XXXXX.X.XXXXX.XXXX.X",
                "X.o..X...............X..o.X",
                "XXXX.X.X.XXXXXXXXX.X.X.XXXX",
                "X......X.....X.....X......X",
                "X.XXXXXXXXXX.X.XXXXXXXXXX.X",
                "X.........................X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXX"
        ).mazeCellDimensions(32, 32)
                .cellImage(new GdxImage(cellRegions[0][0]));

        var pacmanImageR = makeAnimatedImage(10, 0, 0,1,2);
        var pacmanImageL = makeAnimatedImage(10, 0, 2,3,4);
        var pacmanImageU = makeAnimatedImage(10, 0, 2,5,6);
        var pacmanImageD = makeAnimatedImage(10, 0, 2,7,8);
        var pacmanDeath = makeAnimatedImage(3, 1, 0,1,2,3,4,5,6,7,8,9,10);

        game = new GameState();
        pacman = new Pacman(game)
                .withImages(pacmanImageL, pacmanImageR, pacmanImageU, pacmanImageD, pacmanDeath)
                .withCollisionRadius(10)
                .defaultSpeed(100)
                .atMazePosition(maze.position(1, 1));

        pacman.move(Direction.RIGHT);

        var ghost1 = makeGhost(2).atMazePosition(maze.position(10, 11));
        var ghost2 = makeGhost(3).atMazePosition(maze.position(11, 11));
        var ghost3 = makeGhost(4).atMazePosition(maze.position(12, 11));
        var ghost4 = makeGhost(5).atMazePosition(maze.position(13, 11));
        ghost1.startSeekingPacman();
        ghost2.startSeekingPacman();
        ghost3.startSeekingPacman();
        ghost4.startSeekingPacman();

        var pillImage = new GdxImage(regions[9][1]);
        var powerPillImage = new GdxImage(regions[9][0]);
        maze.forEachPillPosition(coords -> new Pill(game)
                .withCollisionRadius(4)
                .atCoordinates(coords)
                .withImage(pillImage));
        maze.forEachPowerPillPosition(coords -> new PowerPill(game)
                .withCollisionRadius(4)
                .atCoordinates(coords)
                .withImage(powerPillImage));
    }

    private AnimatedImage makeAnimatedImage(float fps, int spriteRow, int... frames) {
        var images = Arrays.stream(frames)
                .mapToObj(frame -> new GdxImage(regions[spriteRow][frame]))
                .toArray(GdxImage[]::new);
        return new AnimatedImage(fps, images);
    }

    private Ghost makeGhost(int spriteRow) {
        var ghost1ImageR = makeAnimatedImage(3, spriteRow, 0,1);
        var ghost1ImageD = makeAnimatedImage(3, spriteRow, 2,3);
        var ghost1ImageL = makeAnimatedImage(3, spriteRow, 4,5);
        var ghost1ImageU = makeAnimatedImage(3, spriteRow, 6,7);
        var scaredGhostImage = makeAnimatedImage(3, 7, 0,1);

        return new Ghost(game)
                .withImages(ghost1ImageL, ghost1ImageR, ghost1ImageU, ghost1ImageD, scaredGhostImage)
                .withCollisionRadius(10);
    }
    @Override
    public void dispose() {
        cellImage.dispose();
        sprites.dispose();
        renderer.dispose();
    }
}
