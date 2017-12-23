package com.uddernetworks.thotdestruction.gfx;

import java.awt.*;

public class Tinter {

    private int[][] originaImage;

    public Tinter(int[][] originaImage) {
        this.originaImage = originaImage;
    }

    public void tintTo(Color newColor) {
        Color black = new Color(0, 0, 0);
        Color transparent = new Color(0, 0, 0, 0);

        for (int x = 0; x < originaImage.length; x++) {
            for (int y = 0; y < originaImage[0].length; y++) {
                Color oldColor = new Color(originaImage[x][y], false);
//                int oldColor = 0xff000000 | originaImage[x][y];

//                if (oldColor.getRGB() == -16777216) {
                if (oldColor.getRGB() != black.getRGB()) {
                    originaImage[x][y] = Color.TRANSLUCENT;
                }
            }
        }
    }

}
