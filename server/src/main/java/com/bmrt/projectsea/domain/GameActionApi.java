package com.bmrt.projectsea.domain;

public interface GameActionApi {

    Ship join(String shipId, String name);

    Ship updateDirection(Direction direction);

    Ship stop();

    void startGame();
}
