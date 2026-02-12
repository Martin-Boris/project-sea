package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DirectionTest {

    @Test
    void opposite_ofRight_isLeft() {
        Assertions.assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
    }

    @Test
    void opposite_ofLeft_isRight() {
        Assertions.assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
    }

    @Test
    void opposite_ofTop_isBot() {
        Assertions.assertEquals(Direction.BOT, Direction.TOP.opposite());
    }

    @Test
    void opposite_ofBot_isTop() {
        Assertions.assertEquals(Direction.TOP, Direction.BOT.opposite());
    }

    @Test
    void nonOppositeOf_excludesOpposite() {
        List<Direction> result = Direction.nonOppositeOf(Direction.RIGHT);

        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.contains(Direction.RIGHT));
        Assertions.assertTrue(result.contains(Direction.TOP));
        Assertions.assertTrue(result.contains(Direction.BOT));
        Assertions.assertFalse(result.contains(Direction.LEFT));
    }

    @Test
    void nonOppositeOf_top_excludesBot() {
        List<Direction> result = Direction.nonOppositeOf(Direction.TOP);

        Assertions.assertEquals(3, result.size());
        Assertions.assertFalse(result.contains(Direction.BOT));
    }
}
