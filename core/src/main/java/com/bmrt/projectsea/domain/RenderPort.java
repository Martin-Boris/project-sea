package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.command.ShootCommand;

public interface RenderPort {

    void initRendering(SeaMap seaMap, GameInstance gameInstance);

    void updateView(Vector myShipPosition, float deltaTime, boolean canShootTarget);

    void renderPortShoot(ShootCommand cmd);

    void triggerStarboardShoot(String shipName);

    void removeTarget();

    void setTarget(Ship ship);

    void add(Ship ship);

    void remove(String shipName);

}
