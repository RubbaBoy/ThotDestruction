package com.uddernetworks.thotdestruction.entities.thot;

import com.uddernetworks.thotdestruction.entities.Mob;
import com.uddernetworks.thotdestruction.gfx.DisplayHealth;
import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.gfx.ThotAnimations;
import com.uddernetworks.thotdestruction.main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BasicThot extends Mob implements Thot {

//    private int[][] pixels;

    private static final int ANIMATION_DELAY = 150;
    private static final int speed = 1;
    private int incrementAmount = speed;
    private int pathIndex = 0;
    private List<Integer> xPath;
    private List<Integer> yPath;
    private DisplayHealth displayHealth;

    private String[] currentAnimations = {"LEFT", "RIGHT", "BACKWARD", "FORWARD"};

    public BasicThot(Game game, int x, int y) {
        super(game, 10, 10, game.getThotAnimationSet(), ThotAnimations.FORWARD_STILL);

        System.out.println("Initial position (" + x + ", " + y + ")");

        setInitialPosition(x, y);

        this.displayHealth = new DisplayHealth(game, this, Color.WHITE, Color.RED);
    }

    @Override
    public void setPath(List<Integer> x, List<Integer> y) {
        this.xPath = x;
        this.yPath = y;
    }

    private int tickCount = 0;

    @Override
    public void tick() {
        tickCount++;

        if (health <= 0) {
            game.getEntityManager().remove(this);
            return;
        }

//        if (tickCount % 2 != 0) return;

        int oldX = x;
        int oldY = y;

        x = xPath.get(pathIndex);
        y = yPath.get(pathIndex);

        pathIndex += incrementAmount;
        if (pathIndex >= xPath.size() || pathIndex < 0) {
            incrementAmount *= -1;
            pathIndex += incrementAmount;
        }

        if (x < oldX) {
            if (lookingDirection != LEFT) {
                changeAnimationSet(ANIMATION_DELAY, game.getThotAnimationSet().getSet(currentAnimations[0]));
                lookingDirection = LEFT;
            }
        } else if (x > oldX) {
            if (lookingDirection != RIGHT) {
                changeAnimationSet(ANIMATION_DELAY, game.getThotAnimationSet().getSet(currentAnimations[1]));
                lookingDirection = RIGHT;
            }
        } else if (y < oldY) {
            if (lookingDirection != BACK) {
                changeAnimationSet(ANIMATION_DELAY, game.getThotAnimationSet().getSet(currentAnimations[2]));
                lookingDirection = BACK;
            }
        } else if (y > oldY) {
            if (lookingDirection != FRONT) {
                changeAnimationSet(ANIMATION_DELAY, game.getThotAnimationSet().getSet(currentAnimations[3]));
                lookingDirection = FRONT;
            }
        }

        super.tick();

        displayHealth.tick();
    }

//    @Override
//    public void render(Screen screen) {
////        x += screen.getXOffset();
////        y += screen.getYOffset();
//
//
//        for (int x = 0; x < pixels.length; x++) {
//            for (int y = 0; y < pixels[0].length; y++) {
////                if (isOutOfBounds(screen, x, y)) continue;
//                if (pixels[x][y] != 0) screen.setRGB(this.x + x, this.y + y, pixels[x][y]);
//            }
//        }
//
////        super.render(screen);
//    }

//    /*
    @Override
    public void render(Screen screen) {
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                screen.setRGB(screen.getXOffset() + x + this.x, screen.getYOffset() + y + this.y, pixels[x][y]);
            }
        }

        this.displayHealth.render();
    }
//    */

    @Override
    public void remove() {

    }
}
