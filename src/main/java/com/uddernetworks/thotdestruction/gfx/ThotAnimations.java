package com.uddernetworks.thotdestruction.gfx;

import java.util.Arrays;

public enum ThotAnimations implements AnimationEnum {

    FORWARD_STILL(new Animation(6, 2, 2, 2, 12, 21, 4, 31)),
    FORWARD_WALK_1(new Animation(8, 2, 2, 2, 12, 21, 4, 31)),
    FORWARD_WALK_2(new Animation(10, 2, 2, 2, 12, 21, 4, 31)),

    BACKWARD_STILL(new Animation(6, 4, 2, 2, 12, 21, 4, 31)),
    BACKWARD_WALK_1(new Animation(8, 4, 2, 2, 12, 21, 4, 31)),
    BACKWARD_WALK_2(new Animation(10, 4, 2, 2, 12, 21, 4, 31)),

    RIGHT_STILL(new Animation(6, 6, 2, 2, 11, 21, 4, 31)),
    RIGHT_WALK_1(new Animation(8, 6, 2, 2, 11, 21, 4, 31)),
    RIGHT_WALK_2(new Animation(10, 6, 2, 2, 11, 21, 4, 31)),

    LEFT_STILL(new Animation(6, 6, 2, 2, 11, 21, 4, 31, true)),
    LEFT_WALK_1(new Animation(8, 6, 2, 2, 11, 21, 4, 31, true)),
    LEFT_WALK_2(new Animation(10, 6, 2, 2, 11, 21, 4, 31, true));

    private Animation animation;

    ThotAnimations(Animation animation) {
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    public static void populateValues(SpriteSheet spriteSheet) {
        Arrays.stream(values()).forEach(value -> value.getAnimation().populateData(spriteSheet));
    }

}
