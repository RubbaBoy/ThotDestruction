package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.gfx.Animation;
import com.uddernetworks.thotdestruction.gfx.AnimationEnum;
import com.uddernetworks.thotdestruction.gfx.AnimationSet;
import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.level.Level;
import com.uddernetworks.thotdestruction.level.tile.Tile;
import com.uddernetworks.thotdestruction.main.Game;

public class Mob extends Entity {

    static final int FRONT = 2;
    static final int BACK = 3;
    static final int LEFT = 0;
    static final int RIGHT = 1;

    int speed = 1;
    int x;
    int y;
    int lookingDirection = FRONT;
    boolean isStill = false;
    private long lastChange = 0;
    long delay = 0;
    private int onIndex = 0;
    private AnimationEnum[] animations;
    private AnimationEnum still;
    Animation current;
    int[][] pixels;

    public Mob(Game game, AnimationEnum still, AnimationEnum... animations) {
        super(game);
        this.still = still;
        pixels = still.getAnimation().getPixels();
        current = still.getAnimation();
        this.animations = animations;
    }

    void setInitialPosition(int x, int y) {
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
        this.animations = game.getAnimationSet().allButFirst(animationEnums);
        onIndex = 0;
        lastChange = System.currentTimeMillis();

        pixels = animations[0].getAnimation().getPixels();
        current = animations[0].getAnimation();
    }

    public void refreshAnimations(String[] currentAnimations) {
        changeAnimationSet(delay, game.getAnimationSet().getSet(currentAnimations[lookingDirection]));
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

    private boolean isOutOfBounds(Screen screen, int x, int y) {
        if (this.x + x < 0 || this.y + y < 0) return true;


        if (x > pixels.length || y > pixels[0].length) return true;

        if (this.x + x >= screen.getWidth() || this.y + y >= screen.getHeight()) return true;

        return false;
    }

    public boolean isSolid(double xOffset, double yOffset, int x, int y, int newX, int newY) {
        if (game.getLevel().outOfBounds(newX - xOffset, newY - yOffset)) return true;

        Tile newTile = game.getLevel().getTileAt(new Double(newX - xOffset).intValue(), new Double(newY - yOffset).intValue());
        return newTile.isSolid();
    }

}
