package com.bmrt.projectsea.domain;

public interface RenderPort {

    void initRendering(SeaMap seaMap, Ship myShip);

    void updateView(Ship myShip, float deltaTime);

    void triggerPortShoot();

    void triggerStarboardShoot();

    void removeTarget();

    void setTarget(Ship ship);

    void add(Ship ship);
}
