package com.uddernetworks.thotdestruction.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(String imageName) {
        try {
            this.image = ImageIO.read(SpriteSheet.class.getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getSub(int sheetX, int sheetY, int tileRight, int tileDown) {
        return getSub(sheetX, sheetY, tileRight, tileDown, false);
    }

    public int[][] getSub(int sheetX, int sheetY, int tileRight, int tileDown, boolean flipped) {
        int[][] ret = new int[tileRight * 16][tileDown * 16];

        for (int x = sheetX; x < sheetX + (tileRight * 16); x++) {
            for (int y = sheetY; y < sheetY + (tileDown * 16); y++) {
                int rgb = image.getRGB(x + (sheetX * 16) - sheetX, y + (sheetY * 16) - sheetY);

                int relX = flipped ? (tileRight * 16) - (x - sheetX) - 1 : x - sheetX;
                int relY = y - sheetY;

                if (new Color(rgb).getAlpha() != 0) ret[relX][relY] = rgb;
            }
        }

        return ret;
    }

    public int[][] getCustomSub(int sheetX, int sheetY, int tileRight, int tileDown) {
        int[][] ret = new int[tileRight][tileDown];

        for (int x = sheetX; x < sheetX + (tileRight); x++) {
            for (int y = sheetY; y < sheetY + (tileDown); y++) {
                int rgb = image.getRGB(x, y);

                if (new Color(rgb).getAlpha() != 0) ret[x - sheetX][y - sheetY] = rgb;
            }
        }

        return ret;
    }
}
