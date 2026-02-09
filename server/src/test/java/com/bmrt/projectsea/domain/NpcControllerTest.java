package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
}
