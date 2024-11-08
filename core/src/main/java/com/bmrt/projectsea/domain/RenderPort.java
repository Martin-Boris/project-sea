package com.bmrt.projectsea.domain;

public interface RenderPort {

    void initRendering(SeaMap seaMap, GameInstance gameInstance);

    void updateView(Vector myShipPosition, float deltaTime, boolean canShootTarget);

    void triggerPortShoot(String shipName);

    void triggerStarboardShoot(String shipName);

    void removeTarget();

    void setTarget(Ship ship);

    void add(Ship ship);

    void remove(String shipName);

}
