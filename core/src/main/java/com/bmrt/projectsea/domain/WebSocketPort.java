package com.bmrt.projectsea.domain;

public interface WebSocketPort {

    void addListener(GameInstance gameInstance);

    void startConnection();

    void updateDirection(Direction direction);

    void stop();

    void shoot(String shooter, String target);
}
