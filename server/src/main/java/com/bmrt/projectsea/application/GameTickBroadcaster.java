package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.ClientCommunicationPort;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Tickable;

import java.util.List;

public class GameTickBroadcaster implements Tickable {

    private final GameInstance gameInstance;
    private final ClientCommunicationPort broadcastPort;

    public GameTickBroadcaster(GameInstance gameInstance, ClientCommunicationPort broadcastPort) {
        this.gameInstance = gameInstance;
        this.broadcastPort = broadcastPort;
    }

    @Override
    public void tick() {
        gameInstance.tick();
        List<Ship> npcUpdates = gameInstance.drainNpcUpdates();
        for (Ship ship : npcUpdates) {
            broadcastPort.sendToAllPLayer(Action.TURN, ship);
        }
    }
}
