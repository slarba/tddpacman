package com.cloudamite.pacman.game;

import java.util.*;
import java.util.stream.Stream;

public class Maze {
    private final String[] maze;
    private final int width, height;
    private float mazeCellWidth = 1;
    private float mazeCellHeight = 1;
    private Image cellImage = Image.NULL;

    public Maze(String... maze) {
        if (maze == null ||
                maze.length == 0 ||
                Arrays.stream(maze).anyMatch(m -> m.length() == 0 || m.length() != maze[0].length())) {
            throw new IllegalArgumentException("maze rows should be of same length");
        }
        this.maze = maze;
        width = maze[0].length();
        height = maze.length;
    }

    public Position position(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            throw new IllegalArgumentException(String.format("coords out of bounds: [%d,%d]", x, y));
        }
        return new Position(x, y);
    }

    private boolean isBlocked(int x, int y) {
        char c = at(x, y);
        return c == 'X' || c == '+';
    }

    private boolean isRenderable(int x, int y) {
        return at(x, y) == 'X';
    }

    private char at(int x, int y) {
        return maze[height - 1 - y].charAt(x);
    }

    public Maze mazeCellDimensions(float width, float height) {
        this.mazeCellWidth = width;
        this.mazeCellHeight = height;
        return this;
    }

    public Maze cellImage(Image image) {
        this.cellImage = image;
        return this;
    }

    public void render(Renderer renderer, Coordinates at) {
        iterateOverPositions((x, y) -> {
            if (isRenderable(x, y)) {
                cellImage.render(renderer, Coordinates.xy(x * mazeCellWidth, y * mazeCellHeight).plus(at));
            }
        });
    }

    private void iterateOverPositions(MazePosCallback cb) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cb.fn(x, y);
            }
        }
    }

    private void iterateOverPositionsMarked(PillPositionCallback cb, char mark) {
        iterateOverPositions((x, y) -> {
            if (at(x, y) == mark) cb.atPosition(position(x, y).toScreenCoordinates());
        });
    }

    public void forEachPillPosition(PillPositionCallback cb) {
        iterateOverPositionsMarked(cb, '.');
    }

    public void forEachPowerPillPosition(PillPositionCallback cb) {
        iterateOverPositionsMarked(cb, 'o');
    }

    private interface MazePosCallback {
        void fn(int x, int y);
    }

    public interface PillPositionCallback {
        void atPosition(Coordinates coordinates);
    }

    private static class PathNode {
        private final Position pos;
        private float g, f;
        private PathNode preceding;

        public PathNode(Position pos, float g, float f) {
            this.pos = pos;
            this.g = g;
            this.f = f;
        }

        public PathNode checkNeighborCostIfBest(PathNode neighbor) {
            var gscore = g + 1;
            if (gscore >= neighbor.g)  return neighbor;
            neighbor.preceding = this;
            neighbor.g = gscore;
            neighbor.f = gscore + pos.distanceTo(neighbor.pos);
            return neighbor;
        }

        public boolean hasReached(Position goal) {
            return pos.equals(goal);
        }

        public Stream<PathNode> neighbors() {
            return pos.neighbors();
        }

        public List<Position> collectPath() {
            var result = new ArrayList<Position>(10);
            var current = this;
            while (current != null) {
                result.add(0, current.pos);
                current = current.preceding;
            }
            return result;
        }

        @Override
        @SuppressWarnings("all")
        public boolean equals(Object o) {
            if (this == o) return true;
            PathNode pathNode = (PathNode) o;
            return pos.equals(pathNode.pos);
        }
    }

    public class Position {
        private final int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position randomPositionOutsideRadius(long seed, float radius) {
            var r = new Random(seed);
            int i = 50;
            while (i > 0) {
                i--;
                var rx = r.nextInt(width);
                var ry = r.nextInt(height);
                if (!isBlocked(rx, ry)) {
                    var candidate = position(rx, ry);
                    if (distanceTo(candidate) >= radius) return candidate;
                }
            }
            return position(1, 1);
        }

        @Override
        @SuppressWarnings("all")
        public boolean equals(Object o) {
            if (this == o) return true;
            Position position = (Position) o;
            if (x != position.x) return false;
            return y == position.y;
        }

        public Position toDirection(Direction dir) {
            int nx = x + dir.xoffset();
            int ny = y + dir.yoffset();
            if (nx < 0) nx = width - 1;
            if (nx >= width) nx = 0;
            if (ny < 0) ny = height - 1;
            if (ny >= height) ny = 0;
            if (isBlocked(nx, ny)) return null;
            return new Position(nx, ny);
        }

        @Override
        public String toString() {
            return String.format("[%d,%d]", x, y);
        }

        public Coordinates toScreenCoordinates() {
            return Coordinates.xy(x * mazeCellWidth, y * mazeCellHeight);
        }

        private Stream<PathNode> neighbors() {
            return Stream.of(
                            toDirection(Direction.DOWN),
                            toDirection(Direction.UP),
                            toDirection(Direction.LEFT),
                            toDirection(Direction.RIGHT)).filter(Objects::nonNull)
                    .map(pos -> new PathNode(pos, Float.MAX_VALUE, Float.MAX_VALUE));
        }

        private float distanceTo(Position pos) {
            float dx = pos.x - x;
            float dy = pos.y - y;
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        public List<Position> shortestPathTo(Position goal) {
            if (isBlocked(goal.x, goal.y) || isBlocked(x, y)) {
                throw new IllegalArgumentException("goal should not be a wall");
            }
            var startNode = new PathNode(this, 0, distanceTo(goal));
            var openSet = new PriorityQueue<PathNode>((a, b) -> Float.compare(a.f, b.f));
            openSet.add(startNode);
            PathNode current = startNode;

            while (!openSet.isEmpty()) {
                current = openSet.poll();
                if (current.hasReached(goal)) break;
                final var curr = current;
                curr.neighbors().forEach(neighbor -> {
                    var n = curr.checkNeighborCostIfBest(neighbor);
                    if (!openSet.contains(n)) openSet.add(n);
                });
            }

            return current.collectPath();
        }

        public Direction directionTo(Position pos) {
            int dx = pos.x - x;
            int dy = pos.y - y;
            if (dx < 0 && dy == 0) return Direction.LEFT;
            if (dx > 0 && dy == 0) return Direction.RIGHT;
            if (dy > 0 && dx == 0) return Direction.UP;
            if (dy < 0 && dx == 0) return Direction.DOWN;
            throw new IllegalArgumentException("not neighbor or is diagonal direction");
        }
    }
}
