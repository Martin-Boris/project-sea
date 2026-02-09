package com.bmrt.projectsea.domain;

import java.util.Collection;

public class NpcController {

    private final Ship ship;
    private final NpcBehavior behavior;

    public NpcController(Ship ship, NpcBehavior behavior) {
        this.ship = ship;
        this.behavior = behavior;
    }

    public void act(Collection<Ship> allShips, SeaMap map, float gameTick) {
        behavior.decideTick(ship, allShips, map, gameTick);
    }

    public Ship getShip() {
        return ship;
    }
}
