package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.*;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import com.bmrt.projectsea.infrastructure.GameLoop;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;

@ApplicationScoped
public class GameInstanceService implements GameActionApi {

    private final GameInstance gameInstance;
    private final GameLoop gameLoop;
    private final ClientCommunicationPort clientCommunicationPort;

    @Inject
    public GameInstanceService(GameInstance gameInstance, GameLoop gameLoop, ClientCommunicationPort clientCommunicationPort) {
        this.gameInstance = gameInstance;
        this.gameLoop = gameLoop;
        this.clientCommunicationPort = clientCommunicationPort;
    }

    @Override
    public Ship join(String name, float x, float y) {
        if (!gameLoop.isRunning()) {
            gameLoop.start();
        }
        Ship ship = gameInstance.playerJoin(name, x, y);
        clientCommunicationPort.sendToAllPLayer(Action.JOIN, ship);
        return ship;
    }

    @Override
    public Ship updateDirection(Direction direction, String name) {
        Ship ship = gameInstance.updateDirection(direction, name, GameLoop.GAME_TICK);
        clientCommunicationPort.sendToAllPLayer(Action.TURN, ship);
        return ship;
    }

    @Override
    public Ship stop(String name) {
        Ship ship = gameInstance.stop(name);
        clientCommunicationPort.sendToAllPLayer(Action.STOP, ship);
        return ship;
    }

    @Override
    public Ship leave(String name) {
        Ship ship = gameInstance.playerLeave(name);
        if (gameLoop.isRunning() && gameInstance.hasNoPlayers()) {
            gameLoop.stop();
        }
        clientCommunicationPort.sendToAllPLayer(Action.LEAVE, ship);
        return ship;
    }

    @Override
    public Collection<Ship> getShips() {
        return gameInstance.getShips();
    }

    @Override
    public Ship shoot(String shooter, String target)
        throws InvalidTarget, TargetToFar {
        Ship ship = gameInstance.shoot(shooter, target);
        clientCommunicationPort.sendToAllPLayer(Action.SHOOT, ship);
        return ship;
    }
}
