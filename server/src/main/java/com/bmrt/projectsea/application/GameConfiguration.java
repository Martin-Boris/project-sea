package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.SeaMap;
import com.bmrt.projectsea.domain.Tickable;
import com.bmrt.projectsea.infrastructure.GameLoop;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class GameConfiguration {

    @Produces
    @ApplicationScoped
    public GameInstance gameInstance() {
        return new GameInstance(new SeaMap(20, 20), GameLoop.GAME_TICK);
    }

    @Produces
    @ApplicationScoped
    public GameLoop gameLoop(Tickable tickable) {
        return new GameLoop(tickable);
    }
}
