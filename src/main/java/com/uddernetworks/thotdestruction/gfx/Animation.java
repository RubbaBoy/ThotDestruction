package com.uddernetworks.thotdestruction.gfx;

public class Animation {

    private int[][] pixels;
    private int gridX;
    private int gridY;
    private int width;
    private int height;

    private int bound_minX;
    private int bound_maxX;

    private int bound_minY;
    private int bound_maxY;

    private boolean flip;

    public Animation(int gridX, int gridY, int width, int height, int bound_minX, int bound_maxX, int bound_minY, int bound_maxY) {
        this(gridX, gridY, width, height, bound_minX, bound_maxX, bound_minY, bound_maxY, false);
    }

    public Animation(int gridX, int gridY, int width, int height, int bound_minX, int bound_maxX, int bound_minY, int bound_maxY, boolean flip) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.width = width;
        this.height = height;
        this.bound_minX = bound_minX;
        this.bound_maxX = bound_maxX;
        this.bound_minY = bound_minY;
        this.bound_maxY = bound_maxY;
        this.flip = flip;
    }

    public void populateData(SpriteSheet spriteSheet) {
        pixels = spriteSheet.getSub(gridX, gridY, width, height, flip);
    }

    public int getBound_minX() {
        return bound_minX;
    }

    public int getBound_maxX() {
        return bound_maxX;
    }

    public int getBound_minY() {
        return bound_minY;
    }

    public int getBound_maxY() {
        return bound_maxY;
    }

    public int[][] getPixels() {
        return pixels;
    }
}
