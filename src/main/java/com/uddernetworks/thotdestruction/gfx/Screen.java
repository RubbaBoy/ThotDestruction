package com.uddernetworks.thotdestruction.gfx;

import com.uddernetworks.thotdestruction.entities.Player;
import com.uddernetworks.thotdestruction.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {

    private Level level;
    private SpriteSheet spriteSheet;
    private BufferedImage image;
    private final int width;
    private final int height;

    public int xOffset = 0;
    public int yOffset = 0;

    private int minXOffset;
    private int maxXOffset;

    private int minYOffset;
    private int maxYOffset;

    public Screen(SpriteSheet spriteSheet, BufferedImage image, Level level, int width, int height) {
        this.spriteSheet = spriteSheet;
        this.image = image;
        this.level = level;
        this.width = width;
        this.height = height;

        minXOffset = 0;
        maxXOffset = -((((level.getTiles().length - 2) * 30)) - width) / 3;

        minYOffset = 0;
        maxYOffset = -((((level.getTiles()[0].length + 3) * 30)) - height) / 3;

        xOffset = 0;
        yOffset = 0;
    }

    private Color transparent = new Color(0, 0, 0, 0);
    public void setRGB(int x, int y, int rgb) {
//        if (new Color(rgb, true).equals(transparent)) return;
        if (new Color(rgb, true).getAlpha() == 0) return;

        if (x < 0 || x >= image.getWidth()) return;
        if (y < 0 || y >= image.getHeight()) return;
        image.setRGB(x, y, rgb);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public boolean setXOffset(int xOffset) { // Returns if screen moved
        if (xOffset > 0) {
            this.xOffset = 0;
            return false;
        }

        if (xOffset < maxXOffset) {
            this.xOffset = maxXOffset;
            return false;
        }

        this.xOffset = xOffset;

        return true;
    }

    public boolean setYOffset(int yOffset) { // Returns if screen moved
        if (yOffset > 0) {
            this.yOffset = 0;
            return false;
        }

        if (yOffset < maxYOffset) {
            this.yOffset = maxYOffset;
            return false;
        }

        this.yOffset = yOffset;

        return true;
    }

    public void render() {
        level.render(image, xOffset, yOffset);
    }

    public boolean canMoveX(int amount) {
        int newOffset = xOffset + amount;
        return !(newOffset < minXOffset || newOffset > maxXOffset);
    }

    private int oldXOffset;
    private int oldYOffset;

    public void backupOffsets() {
        oldXOffset = xOffset;
        oldYOffset = yOffset;
    }

    public void restoreOffsets() {
        xOffset = oldXOffset;
        yOffset = oldYOffset;
    }
}
