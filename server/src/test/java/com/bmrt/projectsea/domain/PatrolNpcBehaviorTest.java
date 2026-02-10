package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class PatrolNpcBehaviorTest {

    private final PatrolNpcBehavior behavior = new PatrolNpcBehavior();
    private final SeaMap map = new SeaMap(20, 20);
    private final float gameTick = 1 / 60f;

    @Test
    void stoppedNpc_startsMovingRight() {
        Ship npc = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.RIGHT, npc.getDirection());
        Assertions.assertNotEquals(Vector.ZERO, npc.getSpeed());
    }

    @Test
    void movingNpc_reversesAtRightBoundary() {
        Ship npc = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        npc.updateDirection(gameTick, Direction.RIGHT);
        Ship nearBoundary = new Ship(new Vector(20, 10), npc.getSpeed(), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(nearBoundary, Collections.singletonList(nearBoundary), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.LEFT, nearBoundary.getDirection());
    }

    @Test
    void movingNpc_reversesAtLeftBoundary() {
        Ship npc = new Ship(new Vector(0, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        npc.updateDirection(gameTick, Direction.LEFT);
        Ship nearBoundary = new Ship(new Vector(0, 10), npc.getSpeed(), Direction.LEFT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(nearBoundary, Collections.singletonList(nearBoundary), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.RIGHT, nearBoundary.getDirection());
    }

    @Test
    void movingNpc_continuesWhenNotAtBoundary() {
        Ship npc = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        npc.updateDirection(gameTick, Direction.RIGHT);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertFalse(changed);
        Assertions.assertEquals(Direction.RIGHT, npc.getDirection());
    }
}
