package com.bmrt.projectsea.domain;

import java.util.Optional;

public class NpcController {

    private final Ship ship;
    private final NpcBehavior behavior;

    public NpcController(Ship ship, NpcBehavior behavior) {
        this.ship = ship;
        this.behavior = behavior;
    }

    public Optional<Ship> update(SeaMap map, float gameTick) {
        Optional<Direction> newDirection = behavior.getNewDirection(ship, map);
        if (newDirection.isPresent()) {
            ship.updateDirection(gameTick, newDirection.get());
            ship.update(map);
            return Optional.of(ship);
        }
        ship.update(map);
        return Optional.empty();
    }

    public Ship getShip() {
        return ship;
    }
}
