package com.uddernetworks.thotdestruction.level;

import com.uddernetworks.thotdestruction.entities.ThotRiflePickup;
import com.uddernetworks.thotdestruction.entities.thot.BasicThot;
import com.uddernetworks.thotdestruction.entities.thot.PathFinder;
import com.uddernetworks.thotdestruction.level.tile.Tile;
import com.uddernetworks.thotdestruction.level.tile.TileUtils;
import com.uddernetworks.thotdestruction.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Level {

    private Game game;
    private BufferedImage image;
    private BufferedImage entityImage;
    private Tile[][] tiles;

    public Level(Game game, String imagePath, String entityPath) {
        this.game = game;
        try {
            image = ImageIO.read(Level.class.getResourceAsStream(imagePath));
            entityImage = ImageIO.read(Level.class.getResourceAsStream(entityPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTiles() {
        System.out.println("Parsing tiles...");

        tiles = new Tile[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                tiles[x][y] = TileUtils.getTileByColor(new Color(image.getRGB(x, y), false));
            }
        }
    }

    public void readEntities() {

        for (int x = 0; x < entityImage.getWidth(); x++) {
            for (int y = 0; y < entityImage.getHeight(); y++) {

                Color color = new Color(entityImage.getRGB(x, y), true);

                if (color.getRed() == 198 && color.getGreen() == 99 && color.getBlue() == 37) {
                    game.getEntityManager().add(new ThotRiflePickup(game, x * 16, y * 16));
                } else if (color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0) {

                    System.out.println("Spawning THOT");

                    PathFinder pathFinder = new PathFinder(entityImage, x, y);
                    BasicThot basicThot = new BasicThot(game, x * 16, y * 16);

                    basicThot.setPath(pathFinder.getXVals(), pathFinder.getYVals());

                    game.getEntityManager().add(basicThot);
                }
            }
        }
    }

    public boolean outOfBounds(double x, double y) {
        return outOfBounds(new Double(x).intValue(), new Double(y).intValue());
    }

    public boolean outOfBounds(int x, int y) {
        if (x < 0) return true;
        if (y < 0) return true;

        if (x >= tiles.length * 16) return true;
        if (y >= (tiles[0].length * 16) - 1) return true;

        return false;
    }

    public Tile getTileAt(int x, int y) {
        x /= 16;
        y /= 16;
        return tiles[x][y];
    }

    public Tile getTileExact(int x, int y) {
        return tiles[x][y];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void render(BufferedImage image, int xOffset, int yOffset) {
        for (int xTile = 0; xTile < tiles.length; xTile++) {
            for (int yTile = 0; yTile < tiles[0].length; yTile++) {
                Tile currTile = tiles[xTile][yTile];
                for (int subX = 0; subX < 16; subX++) {
                    for (int subY = 0; subY < 16; subY++) {
                        int writeImageX = xOffset + (xTile * 16) + subX;
                        int writeImageY = yOffset + (yTile * 16) + subY;

                        if (writeImageX >= 0 && writeImageX < image.getWidth() && writeImageY >= 0 && writeImageY < image.getHeight()) {

                            image.setRGB(writeImageX, writeImageY, currTile.getColors()[subX][subY]);
                        }
                    }
                }
            }
        }
    }
}

