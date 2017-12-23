package com.uddernetworks.thotdestruction.gfx;

import java.awt.*;

public class FontManager {

    private Font font;

    public FontManager() {
        this.font = new Font();
        this.font.initializeFont();
    }

    public void drawString(Screen screen, String text, Color color, int xPos, int yPos) {
        for (int i = 0; i < text.length(); i++) {
            int[][] charPixels = font.getCharacter(text.charAt(i)).clone();

            Tinter tinter = new Tinter(charPixels);
            tinter.tintTo(color);

            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    if (charPixels[x][y] != Color.TRANSLUCENT) {
                        screen.setRGB(x + xPos + (i * 16), y + yPos, charPixels[x][y]);
                    }
                }
            }
        }
    }

}
