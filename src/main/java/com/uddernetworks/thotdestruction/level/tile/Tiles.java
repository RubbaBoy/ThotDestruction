package com.uddernetworks.thotdestruction.level.tile;

public enum Tiles {
    GRASS(new BasicTile("Grass", 0, 255, 0, 0, 0)),
    STONE(new HardTile("Stone", 64, 64, 64, 1, 0)),
    VOID(new HardTile("Void", 0, 0, 0, 0, 1));

    private Tile tile;

    Tiles(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
