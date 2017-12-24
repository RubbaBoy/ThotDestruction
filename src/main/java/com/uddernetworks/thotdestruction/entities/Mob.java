package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.gfx.Animation;
import com.uddernetworks.thotdestruction.gfx.AnimationEnum;
import com.uddernetworks.thotdestruction.gfx.AnimationSet;
import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.level.Level;
import com.uddernetworks.thotdestruction.level.tile.Tile;
import com.uddernetworks.thotdestruction.main.Game;

public class Mob extends Entity {

    protected static final int FRONT = 2;
    protected static final int BACK = 3;
    protected static final int LEFT = 0;
    protected static final int RIGHT = 1;

    protected int speed = 1;
    protected double health;
    protected double maxHealth;
//    protected int x;
//    protected int y;
    protected int lookingDirection = FRONT;
    protected boolean isStill = false;
    protected long lastChange = 0;
    protected long delay = 0;
    private int onIndex = 0;
    private AnimationEnum[] animations;
    private AnimationSet animationSet;
    private AnimationEnum still;
    protected Animation current;
    protected int[][] pixels;

    protected int xExact;
    protected int yExact;

    public Mob(Game game, int health, int maxHealth, AnimationSet animationSet, AnimationEnum still, AnimationEnum... animations) {
        super(game);
        this.health = health;
        this.maxHealth = maxHealth;
        this.animationSet = animationSet;
        this.still = still;
        pixels = still.getAnimation().getPixels();
        current = still.getAnimation();
        this.animations = animations;
    }

    public void damage(int amount) {
        health -= amount;
    }

    @Override
    public int getCenterX() {
        return x + pixels.length;
    }

    @Override
    public int getCenterY() {
        return y + pixels[0].length;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    protected void setInitialPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void updateAnimation() {
        if (isStill) {
            pixels = still.getAnimation().getPixels();
            current = still.getAnimation();
            lastChange = System.currentTimeMillis();
            return;
        }

        if (animations.length > 1) {
            if (System.currentTimeMillis() - lastChange >= delay / speed) {
                lastChange = System.currentTimeMillis();
                onIndex++;

                if (onIndex >= animations.length) onIndex = 0;

                pixels = animations[onIndex].getAnimation().getPixels();
                current = animations[onIndex].getAnimation();
            }
        }
    }

    public void changeAnimationSet(long delay, AnimationEnum[] animationEnums) {
        this.delay = delay;

        this.still = animationEnums[0];
        this.animations = animationSet.allButFirst(animationEnums);
        onIndex = 0;
        lastChange = System.currentTimeMillis();

        pixels = animations[0].getAnimation().getPixels();
        current = animations[0].getAnimation();
    }

    public void refreshAnimations(String[] currentAnimations) {
        changeAnimationSet(delay, animationSet.getSet(currentAnimations[lookingDirection]));
    }

    public void changeAnimationSet(AnimationEnum animation) {
        this.delay = 0;

        this.still = animation;
        this.animations = new AnimationEnum[] {};
        onIndex = 0;
        lastChange = System.currentTimeMillis();

        pixels = animations[0].getAnimation().getPixels();
        current = animations[0].getAnimation();
    }

    @Override
    public void tick() {
        if (!alive) return;



        this.xExact = x - game.getScreen().getXOffset();
        this.yExact = y - game.getScreen().getYOffset();

        updateAnimation();
    }

    @Override
    public void render(Screen screen) {
        if (!alive) return;

        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                if (isOutOfBounds(screen, x, y)) continue;
                if (pixels[x][y] != 0) screen.setRGB(this.x + x, this.y + y, pixels[x][y]);
            }
        }
    }

    @Override
    public void remove() {
        game.getEntityManager().remove(this);
        alive = false;
    }

    public int getScreenRelativeX() {
        return this.x + (pixels.length / 2);
    }

    public int getScreenRelativeY() {
        return this.y + (pixels[0].length / 2);
    }

    public boolean intersects(int xPos, int yPos) {
        int xMin = x + current.getBound_minX();
        int xMax = x + current.getBound_maxX();

        int yMin = y + current.getBound_minY();
        int yMax = y + current.getBound_maxY();

        return xMin < xPos && xMax > xPos && yMin < yPos && yMax > yPos;
    }

    protected boolean hasCollided(int newX, int newY) {
        int xMin = current.getBound_minX();
        int xMax = current.getBound_maxX();

        int yMin = current.getBound_minY();
        int yMax = current.getBound_maxY();


        for (int x = xMin; x < xMax; x++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, x + newX, newY + yMin)) {
                return true;
            }
        }

        for (int x = xMin; x < xMax; x++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, x + newX, newY + yMax)) {
                return true;
            }
        }


        for (int y = yMin; y < yMax; y++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, newX + xMin, y + newY)) {
                return true;
            }
        }

        for (int y = yMin; y < yMax; y++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, newX + xMax, y + newY)) {
                return true;
            }
        }

        return false;
    }

    private boolean isOutOfBounds(Screen screen, int x, int y) {
        if (this.x + x < 0 || this.y + y < 0) return true;


        if (x > pixels.length || y > pixels[0].length) return true;

        if (this.x + x >= screen.getWidth() || this.y + y >= screen.getHeight()) return true;

        return false;
    }

    public boolean isSolid(double xOffset, double yOffset, int newX, int newY) {
        if (game.getLevel().outOfBounds(newX - xOffset, newY - yOffset)) return true;

        Tile newTile = game.getLevel().getTileAt(new Double(newX - xOffset).intValue(), new Double(newY - yOffset).intValue());
        return newTile.isSolid();
    }

}
