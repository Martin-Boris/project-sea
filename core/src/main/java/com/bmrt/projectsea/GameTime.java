package com.bmrt.projectsea;

public class GameTime {

    private static float currentTime = 0;

    public static float getCurrentTime() {
        return currentTime;
    }

    static void updateCurrentTime(float addValue) {
        currentTime += addValue;
    }
}
