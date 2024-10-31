package com.bmrt.projectsea.domain;

public class SeaMap {

    public static final float UNIT = 1 / 32f;
    private final int width;
    private final int height;

    public SeaMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOut(Vector position) {
        return position.getX() < 0
            || position.getY() < 0
            || position.getX() > width
            || position.getY() > height;
    }

}
