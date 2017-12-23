package com.uddernetworks.thotdestruction.level.tile;

import com.uddernetworks.thotdestruction.gfx.SpriteSheet;
import com.uddernetworks.thotdestruction.level.tile.Tile;
import com.uddernetworks.thotdestruction.level.tile.Tiles;

import java.awt.*;
import java.util.Arrays;

public class TileUtils {

    public static Tile getTileByColor(int r, int g, int b) {
        return getTileByColor(new Color(r, g, b));
    }

    public static Tile getTileByColor(Color color) {
        for (Tiles tiles : Tiles.values()) {
            Tile tile = tiles.getTile();
            if (tile.getColor().equals(color)) return tile;
        }

        return Tiles.VOID.getTile();
    }

    public static void loadAllTiles(SpriteSheet spriteSheet) {
        Arrays.stream(Tiles.values()).forEach(tile -> tile.getTile().loadPixels(spriteSheet));
    }

}
