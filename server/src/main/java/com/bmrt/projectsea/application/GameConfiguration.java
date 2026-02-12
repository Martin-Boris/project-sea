package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.*;
import com.bmrt.projectsea.infrastructure.GameLoop;
import com.bmrt.projectsea.infrastructure.JavaRandomProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.List;

@ApplicationScoped
public class GameConfiguration {

    @Produces
    @ApplicationScoped
    public GameInstance gameInstance() {
        JavaRandomProvider random = new JavaRandomProvider();
        SeaMap map = new SeaMap(50, 50);
        List<NpcController> npcControllers = Ship.generateNpc(10, map.getWidth(), map.getHeight(), random);
        return new GameInstance(map, npcControllers);
    }

    @Produces
    @ApplicationScoped
    public GameLoop gameLoop(Tickable tickable) {
        return new GameLoop(tickable);
    }

    @Produces
    @ApplicationScoped
    public Tickable tickable(GameInstance gameInstance, ClientCommunicationPort broadcastPort) {
        return new GameTickBroadcaster(gameInstance, broadcastPort);
    }

}
