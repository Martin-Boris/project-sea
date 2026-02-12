package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.Mockito.*;

class NpcControllerTest {

    @Test
    void caseNoDirectionUpdate_ThenOnlyUpdatePosition() {
        Ship ship = new Ship(new Vector(0, 0), new Vector(1, 0), Direction.TOP, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        Mockito.when(behavior.getNewDirection(ship, map)).thenReturn(Optional.empty());
        NpcController controller = new NpcController(ship, behavior);
        float gameTick = 1 / 60f;

        Optional<Ship> shipWithUpdatedDirection = controller.update(map, gameTick);

        verify(behavior).getNewDirection(ship, map);
        Assertions.assertEquals(Direction.TOP, ship.getDirection());
        Assertions.assertEquals(new Vector(1, 0), ship.getSpeed());
        Assertions.assertEquals(new Vector(1, 0), ship.getPosition());
        Assertions.assertTrue(shipWithUpdatedDirection.isEmpty());
    }

    @Test
    void caseDirectionUpdate_UpdatePositionAndReturnsShip() {
        Ship ship = new Ship(new Vector(0, 0), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        when(behavior.getNewDirection(ship, map)).thenReturn(of(Direction.RIGHT));
        NpcController controller = new NpcController(ship, behavior);

        Optional<Ship> result = controller.update(map, gameTick);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(ship, result.get());
        Assertions.assertEquals(Direction.RIGHT, ship.getDirection());
        Assertions.assertEquals(new Vector(0.06666667f, 0), ship.getSpeed());
        Assertions.assertEquals(new Vector(0.06666667f, 0), ship.getPosition());
    }

}
