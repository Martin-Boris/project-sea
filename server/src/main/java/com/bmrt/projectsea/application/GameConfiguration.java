package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.*;
import com.bmrt.projectsea.infrastructure.GameLoop;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class GameConfiguration {

    @Produces
    @ApplicationScoped
    public GameInstance gameInstance() {
        PatrolNpcBehavior patrolBehavior = new PatrolNpcBehavior();

        List<NpcController> npcControllers = Arrays.asList(
            new NpcController(
                new Ship(new Vector(5, 5), Vector.ZERO, Direction.BOT, "NPC-Guard1", Ship.MAX_HP, Ship.MAX_HP),
                patrolBehavior
            ),
            new NpcController(
                new Ship(new Vector(15, 10), Vector.ZERO, Direction.BOT, "NPC-Guard2", Ship.MAX_HP, Ship.MAX_HP),
                patrolBehavior
            )
        );

        return new GameInstance(new SeaMap(20, 20), GameLoop.GAME_TICK, npcControllers);
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
