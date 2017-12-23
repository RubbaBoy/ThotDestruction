package com.uddernetworks.thotdestruction.level.tile;

import com.uddernetworks.thotdestruction.gfx.SpriteSheet;
import com.uddernetworks.thotdestruction.level.tile.Tile;

import java.awt.*;

public class BasicTile implements Tile {

    String name;
    private final int spritesheetX;
    private final int spritesheetY;
    private Color color;
    private int[][] pixels;
    boolean isHard = false;

    public BasicTile(String name, int r, int g, int b, int spritesheetX, int spritesheetY) {
        this.spritesheetX = spritesheetX;
        this.spritesheetY = spritesheetY;
        color = new Color(r, g, b);
        this.name = name;
    }

    @Override
    public int getSpritesheetX() {
        return spritesheetX;
    }

    @Override
    public int getSpritesheetY() {
        return spritesheetY;
    }

    @Override
    public void loadPixels(SpriteSheet spriteSheet) {
        System.out.println("Loading pixels");
        this.pixels = spriteSheet.getSub(spritesheetX, spritesheetY, 1, 1);
    }

    @Override
    public int[][] getColors() {
        return pixels;
    }

    @Override
    public boolean isSolid() {
        return isHard;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void render(int x, int y) {

    }

    @Override
    public String toString() {
        return "Tile[" + name + "]";
    }
}
