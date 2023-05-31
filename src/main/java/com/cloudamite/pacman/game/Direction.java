package com.cloudamite.pacman.game;

public enum Direction {
    UP {
        @Override
        public int xoffset() {
            return 0;
        }

        @Override
        public int yoffset() {
            return 1;
        }

        @Override
        public boolean oppositeTo(Direction dir) {
            return dir.equals(DOWN);
        }
    },
    DOWN {
        @Override
        public int xoffset() {
            return 0;
        }

        @Override
        public int yoffset() {
            return -1;
        }

        @Override
        public boolean oppositeTo(Direction dir) {
            return dir.equals(UP);
        }

    },
    LEFT {
        @Override
        public int xoffset() {
            return -1;
        }

        @Override
        public int yoffset() {
            return 0;
        }

        @Override
        public boolean oppositeTo(Direction dir) {
            return dir.equals(RIGHT);
        }
    },
    RIGHT {
        @Override
        public int xoffset() {
            return 1;
        }

        @Override
        public int yoffset() {
            return 0;
        }

        @Override
        public boolean oppositeTo(Direction dir) {
            return dir.equals(LEFT);
        }
    };

    public abstract int xoffset();

    public abstract int yoffset();

    public abstract boolean oppositeTo(Direction dir);
}
