package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;

import java.util.Collection;

public interface GameActionApi {

    Ship join(String name, float x, float y, ClientCommunicationPort clientCommunicationPort);

    Ship updateDirection(Direction direction, String name);

    Ship stop(String name);

    void stopGame();

    void startGame();

    Ship leave(String name);

    Collection<Ship> getShips();

    Ship shoot(String shooter, String target) throws InvalidTarget, TargetToFar;
}
