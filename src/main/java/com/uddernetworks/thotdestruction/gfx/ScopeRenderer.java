package com.uddernetworks.thotdestruction.gfx;

import com.uddernetworks.thotdestruction.main.Game;

import java.awt.*;

public class ScopeRenderer {

    private int[][] scopePixels;
    private boolean enabled = false;
    private Game game;

    private int mouseX;
    private int mouseY;

    private static final boolean DRAW_CROSSHAIR = true;
    private static final boolean DRAW_LINE = false;
    private static final int LINE_LENGTH = 25;

    public ScopeRenderer(SpriteSheet spriteSheet, Game game) {
        scopePixels = spriteSheet.getSub(14, 2, 2, 2);
        this.game = game;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void tick() {
        if (!enabled) return;

        Point point = game.getMousePosition();

        if (point == null) return;

        mouseX = new Double(point.getX()).intValue() / Game.SCALE;
        mouseY = new Double(point.getY()).intValue() / Game.SCALE;
    }

    public void render(Screen screen) {
        if (!enabled) return;


        drawLine(screen, game.getPlayer().getScreenRelativeX(), game.getPlayer().getScreenRelativeY(), mouseX, mouseY, DRAW_LINE ? LINE_LENGTH : -1);

        if (!DRAW_CROSSHAIR) return;

        for (int x = 0; x < scopePixels.length; x++) {
            for (int y = 0; y < scopePixels[0].length; y++) {
                if (scopePixels[x][y] == Color.BLACK.getRGB()) {
                    int imageX = mouseX + x - (scopePixels.length / 2);
                    int imageY = mouseY + y - (scopePixels[0].length / 2);

                    if (imageX < 0) imageX = 0;
                    if (imageX >= screen.getWidth()) imageX = screen.getWidth();

                    if (imageY < 0) imageY = 0;
                    if (imageY >= screen.getHeight()) imageY = screen.getHeight();

                    screen.setRGB(imageX, imageY, scopePixels[x][y]);
                }
            }
        }


    }

    private void drawLine(Screen screen, int x, int y, int x2, int y2, int length) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) dx1 = -1;
        else if (w > 0) dx1 = 1;
        if (h < 0) dy1 = -1;
        else if (h > 0) dy1 = 1;
        if (w < 0) dx2 = -1;
        else if (w > 0) dx2 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) dy2 = -1;
            else if (h > 0) dy2 = 1;
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {
            if (length == -1 || length <= 0) return;
            length--;
            screen.setRGB(x, y, Color.BLACK.getRGB());
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
    }
}
