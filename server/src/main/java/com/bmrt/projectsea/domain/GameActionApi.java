package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;

import java.util.Collection;

public interface GameActionApi {

    Ship join(String name, float x, float y, ClientCommunicationPort clientCommunicationPort);

    Ship updateDirection(Direction direction, String name, ClientCommunicationPort clientCommunicationPort);

    Ship stop(String name, ClientCommunicationPort clientCommunicationPort);

    void stopGame();

    void startGame();

    Ship leave(String name, ClientCommunicationPort clientCommunicationPort);

    Collection<Ship> getShips();

    Ship shoot(String shooter, String target, ClientCommunicationPort clientCommunicationPort) throws InvalidTarget,
        TargetToFar;
}
