package com.uddernetworks.thotdestruction.entities.bullet;

import com.uddernetworks.thotdestruction.entities.Entity;
import com.uddernetworks.thotdestruction.entities.Mob;
import com.uddernetworks.thotdestruction.entities.thot.BasicThot;
import com.uddernetworks.thotdestruction.main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bullet {

    private int[][] pixels;
    private final int startX;
    private final int startY;
    private final int targetX;
    private final int targetY;
    private Game game;
    private int currentLoc = 0;
    private int speed;
    private List<Integer> xPath = new ArrayList<>();
    private List<Integer> yPath = new ArrayList<>();

    public Bullet(Game game, int speed, int startX, int startY, int targetX, int targetY) {
        this.game = game;
        this.speed = speed;

        pixels = game.getSpriteSheet().getSub(14, 2, 2, 2);
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;

        init(startX, startY, targetX, targetY);
    }

    public void init(int startX, int startY, int targetX, int targetY) {
        getPath(startX, startY, targetX, targetY, 300); // 600 pixels is the maximum distance a bullet can go
    }

    public void tick() {
        currentLoc += speed;

        if (currentLoc > xPath.size() || currentLoc > yPath.size() || game.getLevel().outOfBounds(xPath.get(currentLoc), yPath.get(currentLoc)) || hasCollided(xPath.get(currentLoc), yPath.get(currentLoc))) {
            game.getBulletManager().removeBullet(this);
            return;
        }

        Mob hitMob = game.getEntityManager().entityIntersects(BasicThot.class, xPath.get(currentLoc), yPath.get(currentLoc));

        if (hitMob == null) return;

        hitMob.damage(1);

        game.getBulletManager().removeBullet(this);
    }

    public void render() {
        int addX = game.getScreen().getXOffset();
        int addY = game.getScreen().getYOffset();

        if (currentLoc < xPath.size()) {
            int drawX = xPath.get(currentLoc) + addX;
            int drawY = yPath.get(currentLoc) + addY;

            drawBullet(drawX, drawY);
        }
    }

    private boolean hasCollided(int xPos, int yPos) {
        return game.getLevel().getTileAt(xPos, yPos).isSolid();
    }

    private void drawBullet(int xPos, int yPos) {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                game.getScreen().setRGB(x + xPos, y + yPos, Color.BLACK.getRGB());
            }
        }
    }

    private void getPath(int x, int y, int x2, int y2, int length) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) dx1 = -1;
        else if (w > 0) dx1 = 1;
        if (h < 0) dy1 = -1;
        else if (h > 0) dy1 = 1;
        if (w < 0) dx2 = -1;
        else if (w > 0) dx2 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) dy2 = -1;
            else if (h > 0) dy2 = 1;
            dx2 = 0;
        }

        int numerator = longest >> 1;

        for (int i = 0; i <= length; i++) {
            xPath.add(x);
            yPath.add(y);
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
    }


}
