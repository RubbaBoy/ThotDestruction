package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.main.Game;
import com.uddernetworks.thotdestruction.weapons.ThotRifle;

public class ThotRiflePickup extends Pickup {

    public ThotRiflePickup(Game game, int x, int y) {
        super(game, new ThotRifle("Thot Rifle", x, y), game.getEntityTextureManager().getTexture(ThotRiflePickup.class), x, y);
    }
}
