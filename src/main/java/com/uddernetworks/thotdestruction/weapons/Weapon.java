package com.uddernetworks.thotdestruction.weapons;

import com.uddernetworks.thotdestruction.entities.Player;
import com.uddernetworks.thotdestruction.main.Game;

public class Weapon implements Equipable {

    protected String name;
    private boolean isScoping = false;
    private boolean hault = false;

    public Weapon(String name) {
        this.name = name;
    }

    @Override
    public void equip(Player player) {
        player.setCurrentAnimations(new String[] {"RIFLE_LEFT", "RIFLE_RIGHT", "RIFLE_BACKWARD", "RIFLE_FORWARD"});
    }

    @Override
    public void unEquip(Player player) {
        player.setCurrentAnimations(new String[] {"LEFT", "RIGHT", "BACKWARD", "FORWARD"});
    }

    private int tickCount = 0;

    @Override
    public void init(Game game) {
        if (game.getInputHandler().shift.isPressed()) {
            isScoping = true;
            game.getScopeRenderer().enable();
            hault = tickCount % 3 != 0;
        } else {
            game.getScopeRenderer().disable();
            isScoping = false;
            hault = false;
        }

        tickCount++;
    }

    @Override
    public boolean allowFast() {
        return !isScoping;
    }

    @Override
    public boolean hault() {
        return hault;
    }
}
