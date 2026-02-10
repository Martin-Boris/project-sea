package com.bmrt.projectsea.infrastructure;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.RandomProvider;

import java.util.Random;

public class JavaRandomProvider implements RandomProvider {

    private final Random random = new Random();

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public Direction nextDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
}
