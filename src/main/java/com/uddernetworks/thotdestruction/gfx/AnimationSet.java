package com.uddernetworks.thotdestruction.gfx;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet {

    private Map<String, AnimationEnum[]> animationMap = new HashMap<>();

    public AnimationSet() {

    }

    public void addSet(String name, AnimationEnum... animations) {
        animationMap.put(name, animations);
    }

    public AnimationEnum[] getSet(String name) {
        return animationMap.get(name);
    }

    public AnimationEnum[] allButFirst(AnimationEnum[] animationEnums) {
        AnimationEnum[] ret = new AnimationEnum[animationEnums.length - 1];

        if (ret.length == 0) return new AnimationEnum[0];

        System.arraycopy(animationEnums, 1, ret, 0, ret.length);

        return ret;
    }

}
