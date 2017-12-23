package com.uddernetworks.thotdestruction.weapons;

import com.uddernetworks.thotdestruction.entities.Player;
import com.uddernetworks.thotdestruction.main.Game;

public interface Equipable {
    void equip(Player player);
    void unEquip(Player player);
    void init(Game game);
    boolean allowFast();
    boolean hault();
}
