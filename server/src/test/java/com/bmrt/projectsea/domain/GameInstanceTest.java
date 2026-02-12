package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

class GameInstanceTest {

    private GameInstance gameInstance;

    @BeforeEach
    void setUp() {
        gameInstance = new GameInstance(new SeaMap(20, 20), Collections.emptyList());
    }

    @Test
    void casePlayerJoin() {
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.BOT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(0, 0)
            .build();

        Ship ship = gameInstance.playerJoin("Name", 0, 0);

        Assertions.assertEquals(expectedShip, ship);
        Assertions.assertTrue(gameInstance.contains(expectedShip));
        Assertions.assertTrue(gameInstance.getPlayerShips().contains(expectedShip));
    }

    @Test
    void casePlayerLeave() {
        Ship ship = gameInstance.playerJoin("Name", 0, 0);

        Ship leaveShip = gameInstance.playerLeave("Name");

        Assertions.assertEquals(ship, leaveShip);
        Assertions.assertFalse(gameInstance.contains(ship));
        Assertions.assertFalse(gameInstance.getPlayerShips().contains(ship));
    }

    @Test
    void caseStop() {
        Ship ship = gameInstance.playerJoin("Name", 0, 0);

        Ship stopShip = gameInstance.stop("Name");

        Assertions.assertEquals(ship, stopShip);
        Assertions.assertEquals(new Vector(0, 0), stopShip.getSpeed());
        Assertions.assertTrue(gameInstance.contains(ship));
    }

    @Test
    void caseUpdateDirection() {
        gameInstance.playerJoin("Name", 0, 0);
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.LEFT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(-0.06666667f, 0.0f)
            .build();

        Ship ship = gameInstance.updateDirection(Direction.LEFT, "Name", 1 / 60f);

        Assertions.assertEquals(expectedShip, ship);
    }

    @Test
    void caseHasNoPlayers() {
        Assertions.assertTrue(gameInstance.hasNoPlayers());

        gameInstance.playerJoin("Name", 0, 0);
        Assertions.assertFalse(gameInstance.hasNoPlayers());

        gameInstance.playerLeave("Name");
        Assertions.assertTrue(gameInstance.hasNoPlayers());
    }

    @Test
    void caseHasNoPlayers_withNpcsOnly() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController npc = new NpcController(npcShip, behavior);

        GameInstance instanceWithNpcs = new GameInstance(new SeaMap(20, 20),
            Collections.singletonList(npc));

        Assertions.assertTrue(instanceWithNpcs.hasNoPlayers());
    }

    @Test
    void caseHasNoPlayers_withNpcsAndPlayer() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController npc = new NpcController(npcShip, behavior);

        GameInstance instanceWithNpcs = new GameInstance(new SeaMap(20, 20),
            Collections.singletonList(npc));
        instanceWithNpcs.playerJoin("Player", 10, 10);

        Assertions.assertFalse(instanceWithNpcs.hasNoPlayers());
    }

    @Test
    void caseTick_callsNpcControllerAct() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController npc = new NpcController(npcShip, behavior);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;

        GameInstance instanceWithNpcs = new GameInstance(map, Collections.singletonList(npc));

        instanceWithNpcs.tick(gameTick);

        Collection<Ship> allShips = instanceWithNpcs.getShips();
        verify(behavior).getNewDirection(npcShip, map);
    }

    @Test
    void caseTick_collectsNpcUpdatesWhenDirectionChanged() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        NpcController npc = new NpcController(npcShip, behavior);
        GameInstance instanceWithNpcs = new GameInstance(map, Collections.singletonList(npc));
        when(behavior.getNewDirection(npcShip, map)).thenReturn(of(Direction.RIGHT));

        List<Ship> updates = instanceWithNpcs.tick(gameTick);

        Assertions.assertEquals(1, updates.size());
        Assertions.assertSame(npcShip, updates.get(0));
    }

    @Test
    void caseTick_noNpcUpdatesWhenDirectionUnchanged() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        NpcController npc = new NpcController(npcShip, behavior);
        GameInstance instanceWithNpcs = new GameInstance(map, Collections.singletonList(npc));
        when(behavior.getNewDirection(npcShip, map)).thenReturn(Optional.empty());

        List<Ship> updates = instanceWithNpcs.tick(gameTick);

        Assertions.assertTrue(updates.isEmpty());
    }

    @Test
    void caseTick_returnsIndependentListsPerTick() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        NpcController npc = new NpcController(npcShip, behavior);
        GameInstance instanceWithNpcs = new GameInstance(map, Collections.singletonList(npc));
        when(behavior.getNewDirection(npcShip, map))
            .thenReturn(of(Direction.RIGHT))
            .thenReturn(empty());

        List<Ship> firstUpdates = instanceWithNpcs.tick(gameTick);
        List<Ship> secondUpdates = instanceWithNpcs.tick(gameTick);

        Assertions.assertEquals(1, firstUpdates.size());
        Assertions.assertTrue(secondUpdates.isEmpty());
    }

    @Test
    void caseNpcShips_appearInGetShips() {
        Ship npcShip1 = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC1", 10000, 10000);
        Ship npcShip2 = new Ship(new Vector(15, 10), Vector.ZERO, Direction.BOT, "NPC2", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);

        GameInstance instanceWithNpcs = new GameInstance(new SeaMap(20, 20), Arrays.asList(
            new NpcController(npcShip1, behavior),
            new NpcController(npcShip2, behavior)
        ));

        Assertions.assertEquals(2, instanceWithNpcs.getShips().size());
        Assertions.assertTrue(instanceWithNpcs.contains(npcShip1));
        Assertions.assertTrue(instanceWithNpcs.contains(npcShip2));
    }

    @Nested
    class shoot {

        @Test
        void caseNoValidTarget() {
            gameInstance.playerJoin("Name", 0, 0);

            Assertions.assertThrows(InvalidTarget.class, () ->
                gameInstance.shoot("Name", "")
            );
        }

        @Test
        void caseTargetToFar() {
            gameInstance.playerJoin("Name", 0, 0);
            gameInstance.playerJoin("Target", 50, 50);

            Assertions.assertThrows(TargetToFar.class, () ->
                gameInstance.shoot("Name", "Target")
            );
        }

        @Test
        void caseShootTriggered() throws TargetToFar, InvalidTarget {
            gameInstance.playerJoin("Name", 0, 0);
            gameInstance.playerJoin("Target", 2, 2);
            Ship expectedShip = ShipBuilder
                .newShip()
                .withDirection(Direction.BOT)
                .withName("Target")
                .withHealthPoint(7500)
                .withMaxHealthPoint(10000)
                .withPosition(2, 2)
                .withSpeed(0, 0)
                .build();

            Ship target = gameInstance.shoot("Name", "Target");

            Assertions.assertEquals(expectedShip, target);
        }
    }
}
