package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.gfx.PlayerAnimations;
import com.uddernetworks.thotdestruction.main.Game;
import com.uddernetworks.thotdestruction.weapons.Equipable;

public class Player extends Mob {

    private Game game;

    private int centerX;
    private int centerY;

    private Equipable equipped = null;

    private String[] currentAnimations = {"LEFT", "RIGHT", "BACKWARD", "FORWARD"};

    public Player(Game game, int xPos, int yPos) {
        super(game, PlayerAnimations.FORWARD_STILL);
        this.game = game;

        centerX = xPos - (pixels.length / 4) * 2;
        centerY = yPos - (pixels.length / 4) * 2;

        setInitialPosition(centerX, centerY);
    }

    private int tickCount = 0;

    public void setCurrentAnimations(String[] currentAnimations) {
        this.currentAnimations = currentAnimations;

        refreshAnimations(currentAnimations);
    }

    @Override
    public void tick() {
        if (!alive) return;

        tickCount++;

        int oldX = x;
        int oldY = y;

        int movedX = 0;
        int movedY = 0;

        if (game.getInputHandler().q.isPressed()) {
            if (equipped != null) {
                this.currentAnimations = new String[] {"LEFT", "RIGHT", "BACKWARD", "FORWARD"};
                equipped.unEquip(this);
                equipped = null;
            }
        }

        boolean allowFast = true;
        if (equipped != null) {
            equipped.init(game);
            allowFast = equipped.allowFast();

            if (equipped.hault()) return;
        }

        if (allowFast) {
            if (game.getInputHandler().ctrl.isPressed()) {
                game.getPlayer().setSpeed(2);
            } else {
                game.getPlayer().setSpeed(1);
            }
        }

        if (game.getInputHandler().left.isPressed() && !game.getInputHandler().right.isPressed()) {
            movedX = -speed;
        } else if (!game.getInputHandler().left.isPressed() && game.getInputHandler().right.isPressed()) {
            movedX = speed;
        }

        if (game.getInputHandler().up.isPressed() && !game.getInputHandler().down.isPressed()) {
            movedY = -speed;
        } else if (!game.getInputHandler().up.isPressed() && game.getInputHandler().down.isPressed()) {
            movedY = speed;
        }

        game.getScreen().backupOffsets();

        if (x == centerX) {
            if (!game.getScreen().setXOffset(game.getScreen().xOffset - movedX)) {
                x += movedX;
            }
        } else {
            x += movedX;
        }

        if (y == centerY) {
            if (!game.getScreen().setYOffset(game.getScreen().yOffset - movedY)) {
                y += movedY;
            }
        } else {
            y += movedY;
        }


        if (hasCollided(oldX, oldY, x, y)) {
            x = oldX;
            y = oldY;
            game.getScreen().restoreOffsets();
        } else {
            if (movedX < 0) {
                if (lookingDirection != LEFT) {
                    changeAnimationSet(250, game.getAnimationSet().getSet(currentAnimations[0]));
                    lookingDirection = LEFT;
                }
            } else if (movedX > 0) {
                if (lookingDirection != RIGHT) {
                    changeAnimationSet(250, game.getAnimationSet().getSet(currentAnimations[1]));
                    lookingDirection = RIGHT;
                }
            } else if (movedY < 0) {
                if (lookingDirection != BACK) {
                    changeAnimationSet(250, game.getAnimationSet().getSet(currentAnimations[2]));
                    lookingDirection = BACK;
                }
            } else if (movedY > 0) {
                if (lookingDirection != FRONT) {
                    changeAnimationSet(250, game.getAnimationSet().getSet(currentAnimations[3]));
                    lookingDirection = FRONT;
                }
            }
        }

        isStill = movedX == 0 && movedY == 0;

        pickupCheck();

        super.tick();
    }

    private void pickupCheck() {
        Pickup pickup = game.getEntityManager().entityWithin(Pickup.class, 16, x - game.getScreen().getXOffset(), y - game.getScreen().getYOffset());

        if (pickup == null) return;

        if (equipped != null) {
            equipped.unEquip(this);
        }

        equipped = pickup.getEquipable();
        equipped.equip(this);


        pickup.remove();
    }


    private boolean hasCollided(int oldX, int oldY, int newX, int newY) {
        int xMin = current.getBound_minX();
        int xMax = current.getBound_maxX();

        int yMin = current.getBound_minY();
        int yMax = current.getBound_maxY();


        for (int x = xMin; x < xMax; x++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, oldX, oldY, x + oldX, newY + yMin)) {
                return true;
            }
        }

        for (int x = xMin; x < xMax; x++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, oldX, oldY, x + oldX, newY + yMax)) {
                return true;
            }
        }


        for (int y = yMin; y < yMax; y++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, oldX, oldY, newX + xMin, y + newY)) {
                return true;
            }
        }

        for (int y = yMin; y < yMax; y++) {
            if (isSolid(game.getScreen().xOffset, game.getScreen().yOffset, oldX, oldY, newX + xMax, y + newY)) {
                return true;
            }
        }

        return false;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
