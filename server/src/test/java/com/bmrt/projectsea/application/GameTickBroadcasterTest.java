package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.ClientCommunicationPort;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.NpcController;
import com.bmrt.projectsea.domain.PatrolNpcBehavior;
import com.bmrt.projectsea.domain.RandomProvider;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

class GameTickBroadcasterTest {

    private RandomProvider neverTriggerRandom() {
        RandomProvider random = mock(RandomProvider.class);
        when(random.nextFloat()).thenReturn(1.0f);
        return random;
    }

    @Test
    void tick_broadcastsNpcDirectionChange() {
        Ship npcShip = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        PatrolNpcBehavior patrolBehavior = new PatrolNpcBehavior(neverTriggerRandom());
        NpcController npc = new NpcController(npcShip, patrolBehavior);
        GameInstance gameInstance = new GameInstance(new SeaMap(20, 20),
            Collections.singletonList(npc));
        ClientCommunicationPort broadcastPort = mock(ClientCommunicationPort.class);

        GameTickBroadcaster broadcaster = new GameTickBroadcaster(gameInstance, broadcastPort);
        broadcaster.tick(1 / 60f);

        verify(broadcastPort).sendToAllPLayer(eq(Action.TURN), eq(npcShip));
    }

    @Test
    void tick_doesNotBroadcastWhenNoDirectionChange() {
        Ship npcShip = new Ship(new Vector(10, 10), Vector.ZERO, Direction.BOT, "NPC", 10000, 10000);
        PatrolNpcBehavior patrolBehavior = new PatrolNpcBehavior(neverTriggerRandom());
        NpcController npc = new NpcController(npcShip, patrolBehavior);
        GameInstance gameInstance = new GameInstance(new SeaMap(20, 20),
            Collections.singletonList(npc));
        ClientCommunicationPort broadcastPort = mock(ClientCommunicationPort.class);

        GameTickBroadcaster broadcaster = new GameTickBroadcaster(gameInstance, broadcastPort);
        // First tick: NPC is stopped, so PatrolBehavior starts it → broadcast
        broadcaster.tick(1 / 60f);

        // Second tick: NPC is moving in the middle of the map → no direction change
        verify(broadcastPort).sendToAllPLayer(eq(Action.TURN), eq(npcShip));

        // Reset and tick again — NPC is now moving, not at boundary
        broadcaster.tick(1 / 60f);
        // Still only 1 invocation total (no second broadcast)
        verify(broadcastPort).sendToAllPLayer(eq(Action.TURN), eq(npcShip));
    }
}
