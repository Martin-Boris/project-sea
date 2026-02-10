package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatrolNpcBehaviorTest {

    private final RandomProvider randomProvider = mock(RandomProvider.class);
    private final PatrolNpcBehavior behavior = new PatrolNpcBehavior(randomProvider);
    private final SeaMap map = new SeaMap(20, 20);
    private final float gameTick = 1 / 60f;

    @Test
    void stoppedNpc_startsMovingRight() {
        when(randomProvider.nextFloat()).thenReturn(1.0f);
        Ship npc = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.RIGHT, npc.getDirection());
        Assertions.assertNotEquals(Vector.ZERO, npc.getSpeed());
    }

    @Test
    void movingNpc_reversesAtRightBoundary() {
        when(randomProvider.nextFloat()).thenReturn(1.0f);
        Ship nearBoundary = new Ship(new Vector(20, 10), new Vector(1, 0), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(nearBoundary, Collections.singletonList(nearBoundary), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.LEFT, nearBoundary.getDirection());
    }

    @Test
    void movingNpc_reversesAtLeftBoundary() {
        when(randomProvider.nextFloat()).thenReturn(1.0f);
        Ship nearBoundary = new Ship(new Vector(0, 10), new Vector(-1, 0), Direction.LEFT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(nearBoundary, Collections.singletonList(nearBoundary), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.RIGHT, nearBoundary.getDirection());
    }

    @Test
    void movingNpc_continuesWhenNotAtBoundary() {
        when(randomProvider.nextFloat()).thenReturn(1.0f);
        Ship npc = new Ship(new Vector(10, 10), new Vector(1, 0), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertFalse(changed);
        Assertions.assertEquals(Direction.RIGHT, npc.getDirection());
    }

    @Test
    void randomTurnTriggers_changesDirection() {
        when(randomProvider.nextFloat()).thenReturn(0.001f);
        when(randomProvider.nextAmong(anyList())).thenReturn(Direction.TOP);
        Ship npc = new Ship(new Vector(10, 10), new Vector(1, 0), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.TOP, npc.getDirection());
    }

    @Test
    void randomTurnDoesNotTrigger_noChange() {
        when(randomProvider.nextFloat()).thenReturn(0.06f);
        Ship npc = new Ship(new Vector(10, 10), new Vector(1, 0), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertFalse(changed);
        Assertions.assertEquals(Direction.RIGHT, npc.getDirection());
    }

    @Test
    void boundaryTakesPriorityOverRandom() {
        when(randomProvider.nextFloat()).thenReturn(0.001f);
        when(randomProvider.nextAmong(anyList())).thenReturn(Direction.TOP);
        Ship nearBoundary = new Ship(new Vector(20, 10), new Vector(1, 0), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(nearBoundary, Collections.singletonList(nearBoundary), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.LEFT, nearBoundary.getDirection());
    }

    @Test
    void stoppedTakesPriorityOverRandom() {
        when(randomProvider.nextFloat()).thenReturn(0.001f);
        when(randomProvider.nextAmong(anyList())).thenReturn(Direction.TOP);
        Ship npc = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.RIGHT, npc.getDirection());
    }

    @Test
    void randomTurnAppliesReturnedDirection() {
        when(randomProvider.nextFloat()).thenReturn(0.001f);
        when(randomProvider.nextAmong(anyList())).thenReturn(Direction.BOT);
        Ship npc = new Ship(new Vector(10, 10), new Vector(1, 0), Direction.RIGHT, "NPC", 10000, 10000);

        boolean changed = behavior.decideTick(npc, Collections.singletonList(npc), map, gameTick);

        Assertions.assertTrue(changed);
        Assertions.assertEquals(Direction.BOT, npc.getDirection());
    }

}
