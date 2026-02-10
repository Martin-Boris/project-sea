package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NpcControllerTest {

    @Test
    void act_delegatesToNpcBehavior() {
        Ship ship = new Ship(new Vector(0, 0), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController controller = new NpcController(ship, behavior);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;

        controller.act(Collections.singletonList(ship), map, gameTick);

        verify(behavior).decideTick(ship, Collections.singletonList(ship), map, gameTick);
    }

    @Test
    void act_returnsShipWhenBehaviorChanged() {
        Ship ship = new Ship(new Vector(0, 0), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        when(behavior.decideTick(ship, Collections.singletonList(ship), map, gameTick)).thenReturn(true);
        NpcController controller = new NpcController(ship, behavior);

        Optional<Ship> result = controller.act(Collections.singletonList(ship), map, gameTick);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(ship, result.get());
    }

    @Test
    void act_returnsEmptyWhenBehaviorDidNotChange() {
        Ship ship = new Ship(new Vector(0, 0), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        when(behavior.decideTick(ship, Collections.singletonList(ship), map, gameTick)).thenReturn(false);
        NpcController controller = new NpcController(ship, behavior);

        Optional<Ship> result = controller.act(Collections.singletonList(ship), map, gameTick);

        Assertions.assertTrue(result.isEmpty());
    }
}
