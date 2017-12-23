package com.uddernetworks.thotdestruction.level.tile;

import com.uddernetworks.thotdestruction.gfx.SpriteSheet;

import java.awt.*;

public interface Tile {
    boolean isSolid();
    Color getColor();
    void render(int x, int y);
    int getSpritesheetX();
    int getSpritesheetY();
    void loadPixels(SpriteSheet spriteSheet);
    int[][] getColors();
}
