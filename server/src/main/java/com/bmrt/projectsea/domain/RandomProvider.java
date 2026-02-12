package com.bmrt.projectsea.domain;

import java.util.List;

public interface RandomProvider {
    float nextFloat();

    Direction nextDirection();

    Direction nextAmong(List<Direction> directions);

    float nextFloat(int maxValue);
}
