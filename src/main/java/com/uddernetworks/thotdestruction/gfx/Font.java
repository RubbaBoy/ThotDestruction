package com.uddernetworks.thotdestruction.gfx;

import java.util.HashMap;
import java.util.Map;

public class Font {

    String[] letters;

    private Map<Character, int[][]> map = new HashMap<>();
    private int[][] unknown;

    public Font() {
        this.letters = new String[]{
                "`1234567890-=~!@",
                "#$%^&*()[]{};:\'\"",
                "\\|,<>./? ",
                "abcdefghijklmnop",
                "qrstuvwxyz",
                "ABCDEFGHIJKLMNOP",
                "QRSTUVWXYZ"
        };
    }

    public int[][] getCharacter(char character) {
        if (!map.containsKey(character)) return unknown;
        return map.get(character);
    }

    public void initializeFont() {
        SpriteSheet fontSpriteSheet = new SpriteSheet("/font.png");

        int y = 0;
        for (String row : this.letters) {
            for (int x2 = 0; x2 < row.length(); x2++) {
                map.put(row.charAt(x2), fontSpriteSheet.getSub(x2, y, 1, 1));
            }

            y++;
        }

        unknown = fontSpriteSheet.getSub(9, 2, 1, 1);
    }

}
