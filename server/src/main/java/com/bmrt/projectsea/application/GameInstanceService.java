package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameActionApi;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.Ship;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;

@ApplicationScoped
public class GameInstanceService implements GameActionApi {

    private GameInstance gameInstance;

    @Inject
    public void initDependencies() {
        gameInstance = new GameInstance();
    }

    @Override
    public Ship join(String name) {
        return gameInstance.join(name);
    }

    @Override
    public Ship updateDirection(Direction direction, String name) {
        return gameInstance.updateDirection(direction, name);
    }

    public Ship stop(String name) {
        return gameInstance.stop(name);
    }

    @Override
    public void stopGame() {
        gameInstance.stopGame();
    }

    @Override
    public void startGame() {
        gameInstance.startGame();
    }

    @Override
    public Ship leave(String name) {
        return gameInstance.leave(name);
    }

    @Override
    public Collection<Ship> getShips() {
        return gameInstance.getShips();
    }

    @Override
    public Ship shoot(String shooter, String target) {
        return gameInstance.shoot(shooter, target);
    }

}
