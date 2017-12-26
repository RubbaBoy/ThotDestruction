package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.entities.bullet.BasicBullet;
import com.uddernetworks.thotdestruction.gfx.PlayerAnimations;
import com.uddernetworks.thotdestruction.gfx.ThotAnimations;
import com.uddernetworks.thotdestruction.main.Game;
import com.uddernetworks.thotdestruction.weapons.Equipable;

public class Player extends Mob {

    private Game game;

    private int centerX;
    private int centerY;

    private Equipable equipped = null;

    private String[] currentAnimations = {"LEFT", "RIGHT", "BACKWARD", "FORWARD"};

    public Player(Game game, int xPos, int yPos) {
        super(game, 20, 20, game.getAnimationSet(), PlayerAnimations.FORWARD_STILL);
        this.game = game;

        centerX = xPos - (pixels.length / 4) * 2;
        centerY = yPos - (pixels.length / 4) * 2;

        setInitialPosition(centerX, centerY);
    }

    public void setCurrentAnimations(String[] currentAnimations) {
        this.currentAnimations = currentAnimations;

        refreshAnimations(currentAnimations);
    }

    public void click(int xx, int yy) {
        if (game.getInputHandler().shift.isPressed() && equipped != null) {
            BasicBullet bullet = new BasicBullet(game, new Double(Math.abs(xExact) + (pixels.length / 2)).intValue(), new Double(Math.abs(yExact) + (pixels[0].length / 2)).intValue(), xx + Math.abs(game.getScreen().getXOffset()), yy + Math.abs(game.getScreen().getYOffset()));
            game.getBulletManager().addBullet(bullet);
        }
    }

    @Override
    public void tick() {
        if (!alive) return;

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

        if (isWithin(x, centerX, speed)) {
            if (!game.getScreen().setXOffset(game.getScreen().xOffset - movedX)) {
                x += movedX;
            }
        } else {
            x += movedX;
        }

        if (isWithin(y, centerY, speed)) {
            if (!game.getScreen().setYOffset(game.getScreen().yOffset - movedY)) {
                y += movedY;
            }
        } else {
            y += movedY;
        }


        if (hasCollided(x, y)) {
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

        this.xExact = game.getScreen().getXOffset() - x;
        this.yExact = game.getScreen().getYOffset() - y;

        super.tick();
    }

    private static boolean isWithin(int num1, int num2, int range) {
        int diff = Math.abs(num1 - num2);
        return diff <= range;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
