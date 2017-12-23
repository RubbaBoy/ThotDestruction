package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.gfx.Screen;
import com.uddernetworks.thotdestruction.main.Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<Entity> entities = new ArrayList<>();
    private Game game;

    public EntityManager(Game game) {
        this.game = game;
    }

    public void add(Entity entity) {
        entities.add(entity);
    }

    public void renderAll(Screen screen) {
        entities.forEach(entity -> entity.render(screen));
    }

    public void tickAll() {
        new ArrayList<>(entities).forEach(Entity::tick);
    }

    public <T extends Entity> T entityWithin(Class<T> type, double distance, int x, int y) {
        double minDistance = -1;
        Entity minEntity = null;

        distance = Math.pow(distance, 2);

        for (Entity entity : entities) {
            if (type.isInstance(entity)) {
                double tempDistance = euclideanDistanceSquared(x, y, entity.getX(), entity.getY());
                if (tempDistance <= distance && (tempDistance < minDistance || minDistance == -1)) {
                    minDistance = tempDistance;
                    minEntity = entity;
                }
            }
        }

        if (minEntity == null) return null;

        return type.cast(minEntity);
    }

    private double euclideanDistanceSquared(int x1, int y1, int x2, int y2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
    }

    public void remove(Entity entity) {
        if (entities.contains(entity)) {
            entities.remove(entity);
        }
    }
}
