package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameActionApi;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.Ship;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Clock;

@ApplicationScoped
public class GameInstanceService implements GameActionApi {

    private GameInstance gameInstance;

    @Inject
    public void initDependencies() {
        gameInstance = new GameInstance(Clock.systemDefaultZone());
    }

    @Override
    public Ship join(String shipId, String name) {
        return gameInstance.join(shipId, name);
    }

    @Override
    public Ship updateDirection(Direction direction) {
        return gameInstance.updateDirection(direction);
    }

    public Ship stop() {
        return gameInstance.stop();
    }

    @Override
    public void startGame() {
        gameInstance.startGame();
    }

}
