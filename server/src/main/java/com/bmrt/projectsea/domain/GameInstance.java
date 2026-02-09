package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameInstance implements Tickable {

    private final Map<String, Ship> ships;
    private final SeaMap map;
    private final float gameTick;
    private final List<NpcController> npcControllers;

    public GameInstance(SeaMap map, float gameTick, List<NpcController> npcControllers) {
        this.ships = new ConcurrentHashMap<>();
        this.map = map;
        this.gameTick = gameTick;
        this.npcControllers = npcControllers;
        for (NpcController npc : npcControllers) {
            Ship npcShip = npc.getShip();
            ships.put(npcShip.getName(), npcShip);
        }
    }

    @Override
    public void tick() {
        npcControllers.forEach(npc -> npc.act(ships.values(), map, gameTick));
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

    public boolean hasNoPlayers() {
        return ships.size() == npcControllers.size();
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
        return ships.get(name).updateDirection(gameTick, direction);
    }

    public Ship stop(String name) {
        ships.get(name).stop();
        return ships.get(name);
    }

    public boolean contains(Ship ship) {
        return ships.containsValue(ship);
    }
}
