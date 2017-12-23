package com.uddernetworks.thotdestruction.entities;

import com.uddernetworks.thotdestruction.gfx.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

public class EntityTextureManager {

    private SpriteSheet spriteSheet;
    private Map<Class<? extends Entity>, int[][]> textures = new HashMap<>();

    public EntityTextureManager(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public void addTexture(Class<? extends Entity> entityClass, int sheetX, int sheetY, int sheetWidth, int sheetHeight) {
        if (!textures.containsKey(entityClass)) {
            textures.put(entityClass, spriteSheet.getSub(sheetX, sheetY, sheetWidth, sheetHeight));
        }
    }

    public int[][] getTexture(Class<? extends Entity> entityClass) {
        if (textures.containsKey(entityClass)) {
            return textures.get(entityClass);
        } else {
            return new int[16][16];
        }
    }

}
