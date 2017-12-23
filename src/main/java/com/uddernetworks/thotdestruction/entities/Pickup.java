package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.main.Game;
import com.uddernetworks.thotdestruction.weapons.Equipable;

import java.awt.*;

public class Pickup extends Entity {

    private Equipable equipable;
    private int[][] pixels;

    public Pickup(Game game, Equipable equipable, int[][] pixels, int x, int y) {
        super(game);
        this.equipable = equipable;
        this.pixels = pixels;
        this.x = x;
        this.y = y;
    }

    public Equipable getEquipable() {
        return equipable;
    }

    @Override
    public void tick() {
        if (!alive) return;
    }

    @Override
    public void render(Screen screen) {
        if (!alive) return;

        Color transparent = new Color(0, 0, 0, 0);

        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                if (!new Color(pixels[x][y], true).equals(transparent)) {
                    screen.setRGB(screen.getXOffset() + this.x + x, screen.getYOffset() + this.y + y, pixels[x][y]);
                }
            }
        }
    }

    @Override
    public void remove() {
        game.getEntityManager().remove(this);
        alive = false;
    }
}
