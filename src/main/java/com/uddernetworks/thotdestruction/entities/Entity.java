package com.uddernetworks.thotdestruction.entities;


import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.main.Game;

public abstract class Entity {

    public int x, y;
    protected boolean alive = false;
    protected Game game;

    public Entity(Game game) {
        init(game);
    }

    public final void init(Game game) {
        this.game = game;
        alive = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void tick();

    public abstract void render(Screen screen);

    public abstract void remove();
}
