package com.bmrt.projectsea.domain;

public interface RenderPort {

    void initRendering(SeaMap seaMap);

    void updateView(Vector myShipPosition, float deltaTime, boolean canShootTarget);

    void triggerPortShoot();

    void triggerStarboardShoot();

    void removeTarget();

    void setTarget(Ship ship);

    void add(Ship ship, boolean myShip);
}
