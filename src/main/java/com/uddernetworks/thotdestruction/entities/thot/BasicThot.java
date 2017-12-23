package com.uddernetworks.thotdestruction.entities.thot;

import com.uddernetworks.thotdestruction.entities.Entity;
import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.main.Game;

import java.util.List;

public class BasicThot extends Entity implements Thot {

    private int[][] pixels;

    private static final int speed = 3;
    private int incrementAmount = speed;
    private int pathIndex = 0;
    private List<Integer> xPath;
    private List<Integer> yPath;

    public BasicThot(Game game, int x, int y) {
        super(game);
        this.x = x;
        this.y = y;

        pixels = game.getEntityTextureManager().getTexture(BasicThot.class);
    }

    @Override
    public void setPath(List<Integer> x, List<Integer> y) {
        this.xPath = x;
        this.yPath = y;
    }

    private int tickCount = 0;

    @Override
    public void tick() {
        tickCount++;
        if (tickCount % 5 != 0) return;

        x = xPath.get(pathIndex);
        y = yPath.get(pathIndex);

        pathIndex += incrementAmount;
        if (pathIndex >= xPath.size() || pathIndex < 0) {
            incrementAmount *= -1;
            pathIndex += incrementAmount;
        }
    }

    @Override
    public void render(Screen screen) {
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                screen.setRGB(screen.getXOffset() + x + this.x, screen.getYOffset() + y + this.y, pixels[x][y]);
            }
        }
    }

    @Override
    public void remove() {

    }
}
