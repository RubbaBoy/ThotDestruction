package com.uddernetworks.thotdestruction.gfx;

import com.uddernetworks.thotdestruction.entities.Mob;
import com.uddernetworks.thotdestruction.main.Game;

import java.awt.*;

public class DisplayHealth {

    private Game game;
    private Mob mob;
    private int[][][] pixels;
    private int index = 0;

    public DisplayHealth(Game game, Mob mob, Color baseColor, Color filledColor) {
        this.game = game;
        this.mob = mob;

        pixels = new int[21][][];

        for (int i = 0; i < 10; i++) {
            pixels[i] = game.getSpriteSheet().getCustomSub(96, 29 - (i * 3), 32, 3);
        }

        for (int i = 0; i < 10; i++) {
            pixels[i + 10] = game.getSpriteSheet().getCustomSub(96 + 32, 29 - (i * 3), 32, 3);
        }

        pixels[20] = game.getSpriteSheet().getCustomSub(96 + 32 + 32, 29, 32, 3);

        for (int i = 0; i < 21; i++) {
            for (int x = 0; x < pixels[i].length; x++) {
                for (int y = 0; y < pixels[i][0].length; y++) {
                    if (pixels[i][x][y] == Color.BLACK.getRGB()) {
                        pixels[i][x][y] = baseColor.getRGB();
                    } else if (pixels[i][x][y] == Color.WHITE.getRGB()) {
                        pixels[i][x][y] = filledColor.getRGB();
                    }
                }
            }
        }
    }

    public void tick() {
//        System.out.println("\t\t\tHealth = " + mob.getHealth() + "/" + mob.getMaxHealth());

        double healthPercentage = mob.getHealth() / mob.getMaxHealth();

//        System.out.println("Health = " + (healthPercentage * 100) + "%");

        int mapped = new Double((healthPercentage - 0) / (1 - 0) * (20 - 0) + 0).intValue();

//        System.out.println("mapped = " + mapped);

        index = mapped;
    }

    public void render() {

        int addX = mob.getX();
        int addY = mob.getY();

//        System.out.println(Arrays.deepToString(pixels[index]));

        for (int x = 0; x < pixels[0].length; x++) {
            for (int y = 0; y < pixels[0][0].length; y++) {
                game.getScreen().setRGB(x + addX + game.getScreen().getXOffset(), y + addY + game.getScreen().getYOffset(), pixels[index][x][y]);
//                game.getScreen().setRGB(x + addX + game.getScreen().getXOffset(), y + addY + game.getScreen().getYOffset(), Color.BLACK.getRGB());
            }
        }
    }

}
