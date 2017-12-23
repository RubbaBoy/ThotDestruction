package com.uddernetworks.thotdestruction.gfx;

import java.util.Arrays;

public enum PlayerAnimations implements AnimationEnum {

    FORWARD_STILL(new Animation(0, 2, 2, 2, 10, 21, 18, 31)),
    FORWARD_WALK_1(new Animation(0, 6, 2, 2, 10, 21, 18, 31)),
    FORWARD_WALK_2(new Animation(2, 6, 2, 2, 10, 21, 18, 31)),

    BACKWARD_STILL(new Animation(0, 4, 2, 2, 10, 21, 18, 31)),
    BACKWARD_WALK_1(new Animation(2, 4, 2, 2, 10, 21, 18, 31)),
    BACKWARD_WALK_2(new Animation(4, 4, 2, 2, 10, 21, 18, 31)),

    RIGHT_STILL(new Animation(2, 2, 2, 2, 10, 21, 18, 31)),
    RIGHT_WALK_1(new Animation(4, 2, 2, 2, 10, 21, 18, 31)),
    RIGHT_WALK_2(new Animation(6, 2, 2, 2, 10, 21, 18, 31)),

    LEFT_STILL(new Animation(8, 2, 2, 2, 10, 21, 18, 31)),
    LEFT_WALK_1(new Animation(10, 2, 2, 2, 10, 21, 18, 31)),
    LEFT_WALK_2(new Animation(12, 2, 2, 2, 10, 21, 18, 31)),


    RIFLE_FORWARD_STILL(new Animation(0, 8, 2, 2, 10, 21, 18, 31)),
    RIFLE_FORWARD_WALK_1(new Animation(0, 12, 2, 2, 10, 21, 18, 31)),
    RIFLE_FORWARD_WALK_2(new Animation(2, 12, 2, 2, 10, 21, 18, 31)),

    RIFLE_BACKWARD_STILL(new Animation(0, 10, 2, 2, 10, 21, 18, 31)),
    RIFLE_BACKWARD_WALK_1(new Animation(2, 10, 2, 2, 10, 21, 18, 31)),
    RIFLE_BACKWARD_WALK_2(new Animation(4, 10, 2, 2, 10, 21, 18, 31)),

    RIFLE_RIGHT_STILL(new Animation(2, 8, 2, 2, 10, 21, 18, 31)),
    RIFLE_RIGHT_WALK_1(new Animation(4, 8, 2, 2, 10, 21, 18, 31)),
    RIFLE_RIGHT_WALK_2(new Animation(6, 8, 2, 2, 10, 21, 18, 31)),

    RIFLE_LEFT_STILL(new Animation(8, 8, 2, 2, 10, 21, 18, 31)),
    RIFLE_LEFT_WALK_1(new Animation(10, 8, 2, 2, 10, 21, 18, 31)),
    RIFLE_LEFT_WALK_2(new Animation(12, 8, 2, 2, 10, 21, 18, 31));

    private Animation animation;

    PlayerAnimations(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    public static void populateValues(SpriteSheet spriteSheet) {
        Arrays.stream(values()).forEach(value -> value.getAnimation().populateData(spriteSheet));
    }
}
