package com.bmrt.projectsea.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Direction {

    BOT, RIGHT, TOP, LEFT;

    public Direction opposite() {
        switch (this) {
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            case TOP:
                return BOT;
            default:
                return TOP;
        }
    }

    public static List<Direction> nonOppositeOf(Direction current) {
        Direction opposite = current.opposite();
        return Arrays.stream(values())
            .filter(d -> d != opposite)
            .collect(Collectors.toList());
    }
}
