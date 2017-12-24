package com.uddernetworks.thotdestruction.entities.bullet;

import com.uddernetworks.thotdestruction.main.Game;

public class BasicBullet extends Bullet {

    public BasicBullet(Game game, int startX, int startY, int targetX, int targetY) {
        super(game, 4, startX, startY, targetX, targetY);
    }
}
