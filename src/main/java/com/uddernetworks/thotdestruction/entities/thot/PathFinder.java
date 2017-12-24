package com.uddernetworks.thotdestruction.entities.thot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathFinder {

    private int x;
    private int y;

    private int moved = 0;
    private List<Integer> xVals = new ArrayList<>();
    private List<Integer> yVals = new ArrayList<>();

    private int[][] pixels;

    private int pathColor;

    public PathFinder(BufferedImage image, int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;

        this.pathColor = new Color(255, 0, 255).getRGB();

        pixels = new int[image.getWidth()][];

        for (int x = 0; x < image.getWidth(); x++) {
            int[] column = new int[image.getHeight()];
            for (int y = 0; y < image.getHeight(); y++) {
                column[y] = image.getRGB(x, y);
                if (column[y] == 16777215) column[y] = -1;
            }

            pixels[x] = column;
        }

        findPath();
    }

    public void findPath() {

        int movedX = 0;
        int movedY = 0;

        if (pixels[x + 1][y] == pathColor) {
            x++;
            movedX++;
        } else if (pixels[x - 1][y] == pathColor) {
            x--;
            movedX--;
        } else if (pixels[x][y + 1] == pathColor) {
            y++;
            movedY++;
        } else if (pixels[x][y - 1] == pathColor) {
            y--;
            movedY--;
        } else {
            return;
        }

        pixels[x][y] = -1;

        if (movedX > 0) {
            for (int i = -16; i < 0; i++) {
                xVals.add(x * 16 + i);
                yVals.add(y * 16 - 16);
            }
        } else if (movedX < 0) {
            for (int i = -16; i < 0; i++) {
                xVals.add(x * 16 - i);
                yVals.add(y * 16 - 16);
            }
        } else if (movedY > 0) {
            for (int i = -16; i < 0; i++) {
                xVals.add(x * 16);
                yVals.add(y * 16 + i - 16);
            }
        } else if (movedY < 0) {
            for (int i = -16; i < 0; i++) {
                xVals.add(x * 16);
                yVals.add(y * 16 - i - 16);
            }
        }

        findPath();
    }

    public List<Integer> getXVals() {
        return xVals;
    }

    public List<Integer> getYVals() {
        return yVals;
    }

}
