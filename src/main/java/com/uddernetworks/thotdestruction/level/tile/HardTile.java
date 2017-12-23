package com.uddernetworks.thotdestruction.level.tile;

public class HardTile extends BasicTile {

    public HardTile(String name, int r, int g, int b, int spritesheetX, int spritesheetY) {
        super(name, r, g, b, spritesheetX, spritesheetY);
        this.isHard = true;
    }

    @Override
    public String toString() {
        return "HardTile[" + name + "]";
    }
}
