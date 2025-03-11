package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;

import java.util.Collection;
import java.util.HashMap;

public class GameInstance implements GameActionApi {

    public static final float GAME_TICK = 1 / 60f;
    public static final float GAME_TICK_NANO = 1 / 60f * 1000000000;


    private final HashMap<String, Ship> ships;
    private final SeaMap map;
    private boolean running;

    public GameInstance() {
        this.running = false;
        this.ships = new HashMap<>();
        this.map = new SeaMap(20, 20);
    }

    private void processGameLoop() {
        long currentTime;
        long dt;
        long previousTickTime = System.nanoTime();
        float accumulator = 0;
        while (running) {
            currentTime = System.nanoTime();
            dt = (currentTime - previousTickTime);
            previousTickTime = currentTime;
            accumulator += dt;
            if (accumulator >= GAME_TICK_NANO) {
                accumulator -= GAME_TICK_NANO;
                ships.values().forEach(ship -> ship.update(map));
            }
        }

    }

    @Override
    public void startGame() {
        if (!this.running) {
            this.running = true;
            Thread gameInstanceThread = new Thread(this::processGameLoop);
            gameInstanceThread.start();
        }
    }

    @Override
    public Ship leave(String name, ClientCommunicationPort clientCommunicationPort) {
        Ship ship = ships.remove(name);
        if (this.running && ships.isEmpty()) {
            running = false;
        }
        clientCommunicationPort.sendToAllPLayer(Action.LEAVE, ship);
        return ship;
    }

    @Override
    public Collection<Ship> getShips() {
        return ships.values();
    }

    @Override
    public Ship shoot(String shooter, String target) throws InvalidTarget, TargetToFar {
        if (!ships.containsKey(target)) {
            throw new InvalidTarget();
        }
        if (!ships.get(shooter).canShoot(ships.get(target))) {
            throw new TargetToFar();
        }
        return ships.get(target).applyDamage(Ship.DAMAGE);
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public Ship join(String name, float x, float y, ClientCommunicationPort clientCommunicationPort) {
        if (!running) {
            startGame();
        }
        Ship ship = new Ship(new Vector(x, y), Vector.ZERO, Direction.BOT, name, 10000, 10000);
        this.ships.put(name, ship);
        clientCommunicationPort.sendToAllPLayer(Action.JOIN, ship);
        return ship;
    }

    @Override
    public Ship updateDirection(Direction direction, String name) {
        return ships.get(name).updateDirection(GAME_TICK, direction);
    }

    @Override
    public Ship stop(String name) {
        return ships.get(name).stop();
    }

    public boolean contains(Ship ship) {
        return ships.containsValue(ship);
    }
}
