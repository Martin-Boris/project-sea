package com.bmrt.projectsea.domain;

import java.util.Collection;
import java.util.Optional;

public class NpcController {

    private final Ship ship;
    private final NpcBehavior behavior;

    public NpcController(Ship ship, NpcBehavior behavior) {
        this.ship = ship;
        this.behavior = behavior;
    }

    public Optional<Ship> act(Collection<Ship> allShips, SeaMap map, float gameTick) {
        boolean changed = behavior.decideTick(ship, allShips, map, gameTick);
        return changed ? Optional.of(ship) : Optional.empty();
    }

    public Ship getShip() {
        return ship;
    }
}
