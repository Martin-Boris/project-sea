package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameInstance {

    public static final float GAME_TICK = 1 / 60f;

    private final Map<String, Ship> ships;
    private final SeaMap map;

    public GameInstance(SeaMap map) {
        this.ships = new HashMap<>();
        this.map = map;
    }

    public void tick() {
        ships.values().forEach(ship -> ship.update(map));
    }

    public Ship join(String name, float x, float y) {
        Ship ship = new Ship(new Vector(x, y), Vector.ZERO, Direction.BOT, name, 10000, 10000);
        this.ships.put(name, ship);
        return ship;
    }

    public Ship leave(String name) {
        return ships.remove(name);
    }

    public Collection<Ship> getShips() {
        return ships.values();
    }

    public boolean isEmpty() {
        return ships.isEmpty();
    }

    public Ship shoot(String shooter, String target) throws InvalidTarget, TargetToFar {
        if (!ships.containsKey(target)) {
            throw new InvalidTarget();
        }
        Ship shooterShip = ships.get(shooter);
        Ship targetShip = ships.get(target);
        if (!shooterShip.canShoot(targetShip)) {
            throw new TargetToFar();
        }
        targetShip.applyDamage(Ship.DAMAGE);
        return targetShip;
    }

    public Ship updateDirection(Direction direction, String name) {
        return ships.get(name).updateDirection(GAME_TICK, direction);
    }

    public Ship stop(String name) {
        ships.get(name).stop();
        return ships.get(name);
    }

    public boolean contains(Ship ship) {
        return ships.containsValue(ship);
    }
}
