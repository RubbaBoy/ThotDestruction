package com.uddernetworks.thotdestruction.entities.bullet;

import com.uddernetworks.thotdestruction.main.Game;

import java.util.ArrayList;
import java.util.List;

public class BulletManager {

    private Game game;
    private List<Bullet> bullets = new ArrayList<>();

    public BulletManager(Game game) {
        this.game = game;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public void tickAll() {
        new ArrayList<>(bullets).forEach(Bullet::tick);
    }

    public void renderAll() {
        new ArrayList<>(bullets).forEach(Bullet::render);
    }

}
