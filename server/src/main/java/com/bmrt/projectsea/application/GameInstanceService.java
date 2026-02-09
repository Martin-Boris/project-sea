package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.ClientCommunicationPort;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameActionApi;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.Ship;
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

    @Inject
    public GameInstanceService(GameInstance gameInstance, GameLoop gameLoop) {
        this.gameInstance = gameInstance;
        this.gameLoop = gameLoop;
    }

    @Override
    public Ship join(String name, float x, float y, ClientCommunicationPort clientCommunicationPort) {
        if (!gameLoop.isRunning()) {
            gameLoop.start();
        }
        Ship ship = gameInstance.join(name, x, y);
        clientCommunicationPort.sendToAllPLayer(Action.JOIN, ship);
        return ship;
    }

    @Override
    public Ship updateDirection(Direction direction, String name, ClientCommunicationPort clientCommunicationPort) {
        Ship ship = gameInstance.updateDirection(direction, name);
        clientCommunicationPort.sendToAllPLayer(Action.TURN, ship);
        return ship;
    }

    @Override
    public Ship stop(String name, ClientCommunicationPort clientCommunicationPort) {
        Ship ship = gameInstance.stop(name);
        clientCommunicationPort.sendToAllPLayer(Action.STOP, ship);
        return ship;
    }

    @Override
    public Ship leave(String name, ClientCommunicationPort clientCommunicationPort) {
        Ship ship = gameInstance.leave(name);
        if (gameLoop.isRunning() && gameInstance.isEmpty()) {
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
    public Ship shoot(String shooter, String target, ClientCommunicationPort clientCommunicationPort)
        throws InvalidTarget, TargetToFar {
        Ship ship = gameInstance.shoot(shooter, target);
        clientCommunicationPort.sendToAllPLayer(Action.SHOOT, ship);
        return ship;
    }
}
