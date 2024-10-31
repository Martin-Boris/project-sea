package com.bmrt.projectsea.domain;

public interface GameActionApi {

    Ship join(String name);

    Ship updateDirection(Direction direction, String name);

    Ship stop(String name);

    void stopGame();

    void startGame();

    Ship leave(String name);
}
