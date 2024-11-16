package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.GameTime;

public class Cooldown {

    private static final float RELOAD_DURATION = 2f;
    private float cooldownTriggerTime = -Float.MAX_VALUE;


    public float getRemainingCooldownTime() {
        return Math.max(0, RELOAD_DURATION - (GameTime.getCurrentTime() - cooldownTriggerTime));
    }

    public void trigger() {
        cooldownTriggerTime = GameTime.getCurrentTime();
    }

    public boolean isReady() {
        return getRemainingCooldownTime() == 0;
    }

    public float getRemainingCooldownPercentage() {
        if ((RELOAD_DURATION - (GameTime.getCurrentTime() - cooldownTriggerTime)) <= 0) {
            return 0.0f;
        }
        return (RELOAD_DURATION - (GameTime.getCurrentTime() - cooldownTriggerTime)) / RELOAD_DURATION;
    }
}
