package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameInstanceTest {

    private GameInstance gameInstance;

    @BeforeEach
    void setUp() {
        gameInstance = new GameInstance(new SeaMap(20, 20), 1 / 60f, Collections.emptyList());
    }

    @Test
    void caseJoin() {
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.BOT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(0, 0)
            .build();

        Ship ship = gameInstance.join("Name", 0, 0);

        Assertions.assertEquals(expectedShip, ship);
        Assertions.assertTrue(gameInstance.contains(expectedShip));
    }

    @Test
    void caseLeave() {
        Ship ship = gameInstance.join("Name", 0, 0);

        Ship leaveShip = gameInstance.leave("Name");

        Assertions.assertEquals(ship, leaveShip);
        Assertions.assertFalse(gameInstance.contains(ship));
    }

    @Test
    void caseStop() {
        Ship ship = gameInstance.join("Name", 0, 0);

        Ship stopShip = gameInstance.stop("Name");

        Assertions.assertEquals(ship, stopShip);
        Assertions.assertEquals(new Vector(0, 0), stopShip.getSpeed());
        Assertions.assertTrue(gameInstance.contains(ship));
    }

    @Test
    void caseUpdateDirection() {
        gameInstance.join("Name", 0, 0);
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.LEFT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(-0.06666667f, 0.0f)
            .build();

        Ship ship = gameInstance.updateDirection(Direction.LEFT, "Name");

        Assertions.assertEquals(expectedShip, ship);
    }

    @Test
    void caseHasNoPlayers() {
        Assertions.assertTrue(gameInstance.hasNoPlayers());

        gameInstance.join("Name", 0, 0);
        Assertions.assertFalse(gameInstance.hasNoPlayers());

        gameInstance.leave("Name");
        Assertions.assertTrue(gameInstance.hasNoPlayers());
    }

    @Test
    void caseHasNoPlayers_withNpcsOnly() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController npc = new NpcController(npcShip, behavior);

        GameInstance instanceWithNpcs = new GameInstance(new SeaMap(20, 20), 1 / 60f,
            Collections.singletonList(npc));

        Assertions.assertTrue(instanceWithNpcs.hasNoPlayers());
    }

    @Test
    void caseHasNoPlayers_withNpcsAndPlayer() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController npc = new NpcController(npcShip, behavior);

        GameInstance instanceWithNpcs = new GameInstance(new SeaMap(20, 20), 1 / 60f,
            Collections.singletonList(npc));
        instanceWithNpcs.join("Player", 10, 10);

        Assertions.assertFalse(instanceWithNpcs.hasNoPlayers());
    }

    @Test
    void caseTick_callsNpcControllerAct() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        NpcController npc = new NpcController(npcShip, behavior);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;

        GameInstance instanceWithNpcs = new GameInstance(map, gameTick, Collections.singletonList(npc));

        instanceWithNpcs.tick();

        Collection<Ship> allShips = instanceWithNpcs.getShips();
        verify(behavior).decideTick(npcShip, allShips, map, gameTick);
    }

    @Test
    void caseTick_collectsNpcUpdatesWhenDirectionChanged() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        NpcController npc = new NpcController(npcShip, behavior);
        GameInstance instanceWithNpcs = new GameInstance(map, gameTick, Collections.singletonList(npc));
        when(behavior.decideTick(npcShip, instanceWithNpcs.getShips(), map, gameTick)).thenReturn(true);

        instanceWithNpcs.tick();
        List<Ship> updates = instanceWithNpcs.drainNpcUpdates();

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
        GameInstance instanceWithNpcs = new GameInstance(map, gameTick, Collections.singletonList(npc));
        when(behavior.decideTick(npcShip, instanceWithNpcs.getShips(), map, gameTick)).thenReturn(false);

        instanceWithNpcs.tick();
        List<Ship> updates = instanceWithNpcs.drainNpcUpdates();

        Assertions.assertTrue(updates.isEmpty());
    }

    @Test
    void caseDrainNpcUpdates_clearsAfterDrain() {
        Ship npcShip = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);
        SeaMap map = new SeaMap(20, 20);
        float gameTick = 1 / 60f;
        NpcController npc = new NpcController(npcShip, behavior);
        GameInstance instanceWithNpcs = new GameInstance(map, gameTick, Collections.singletonList(npc));
        when(behavior.decideTick(npcShip, instanceWithNpcs.getShips(), map, gameTick)).thenReturn(true);

        instanceWithNpcs.tick();
        instanceWithNpcs.drainNpcUpdates();
        List<Ship> secondDrain = instanceWithNpcs.drainNpcUpdates();

        Assertions.assertTrue(secondDrain.isEmpty());
    }

    @Test
    void caseNpcShips_appearInGetShips() {
        Ship npcShip1 = new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC1", 10000, 10000);
        Ship npcShip2 = new Ship(new Vector(15, 10), Vector.ZERO, Direction.BOT, "NPC2", 10000, 10000);
        NpcBehavior behavior = mock(NpcBehavior.class);

        GameInstance instanceWithNpcs = new GameInstance(new SeaMap(20, 20), 1 / 60f, Arrays.asList(
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
            gameInstance.join("Name", 0, 0);

            Assertions.assertThrows(InvalidTarget.class, () ->
                gameInstance.shoot("Name", "")
            );
        }

        @Test
        void caseTargetToFar() {
            gameInstance.join("Name", 0, 0);
            gameInstance.join("Target", 50, 50);

            Assertions.assertThrows(TargetToFar.class, () ->
                gameInstance.shoot("Name", "Target")
            );
        }

        @Test
        void caseShootTriggered() throws TargetToFar, InvalidTarget {
            gameInstance.join("Name", 0, 0);
            gameInstance.join("Target", 2, 2);
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
