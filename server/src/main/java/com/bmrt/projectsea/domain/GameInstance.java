package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameInstance {

    private final Map<String, Ship> ships;
    private final SeaMap map;
    private final List<NpcController> npcControllers;
    private final List<Ship> playerShips;

    public GameInstance(SeaMap map, List<NpcController> npcControllers) {
        this.ships = new ConcurrentHashMap<>();
        this.map = map;
        this.npcControllers = npcControllers;
        for (NpcController npc : npcControllers) {
            Ship npcShip = npc.getShip();
            ships.put(npcShip.getName(), npcShip);
        }
        this.playerShips = new ArrayList<>();
    }

    public List<Ship> tick(float deltaTime) {
        List<Ship> npcUpdates = new ArrayList<>();
        npcControllers.forEach(npc ->
            npc.update(map, deltaTime).ifPresent(npcUpdates::add)
        );
        playerShips.forEach(ship -> ship.update(map));
        return npcUpdates;
    }

    public Ship playerJoin(String name, float x, float y) {
        Ship ship = new Ship(new Vector(x, y), Vector.ZERO, Direction.BOT, name, 10000, 10000);
        this.ships.put(name, ship);
        this.playerShips.add(ship);
        return ship;
    }

    public Ship playerLeave(String name) {
        this.playerShips.removeIf(ship -> ship.getName().equals(name));
        return ships.remove(name);
    }

    public Collection<Ship> getShips() {
        return ships.values();
    }

    public Collection<Ship> getPlayerShips() {
        return playerShips;
    }

    public boolean hasNoPlayers() {
        return this.playerShips.isEmpty();
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

    public Ship updateDirection(Direction direction, String name, float deltaTime) {
        return ships.get(name).updateDirection(deltaTime, direction);
    }

    public Ship stop(String name) {
        ships.get(name).stop();
        return ships.get(name);
    }

    public boolean contains(Ship ship) {
        return ships.containsValue(ship);
    }
}
