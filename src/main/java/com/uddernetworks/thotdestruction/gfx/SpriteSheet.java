package com.uddernetworks.thotdestruction.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private String path;
    private int width;
    private int height;

    private BufferedImage image;

    public SpriteSheet(String imageName) {
        try {
            this.image = ImageIO.read(SpriteSheet.class.getResourceAsStream(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.image == null) return;

        this.path = imageName;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int[][] getSub(int sheetX, int sheetY, int tileRight, int tileDown) {
        int[][] ret = new int[tileRight * 16][tileDown * 16];

        for (int x = sheetX; x < sheetX + (tileRight * 16); x++) {
            for (int y = sheetY; y < sheetY + (tileDown * 16); y++) {
                int rgb = image.getRGB(x + (sheetX * 16) - sheetX, y + (sheetY * 16) - sheetY);

                if (new Color(rgb).getAlpha() != 0) ret[x - sheetX][y - sheetY] = rgb;
            }
        }

        return ret;
    }

}
