package com.bmrt.projectsea.domain;

import java.util.ArrayList;

public class GameInstance implements GameActionApi {

    public static final float GAME_TICK = 1 / 60f;
    public static final float GAME_TICK_NANO = 1 / 60f * 1000000000;


    private final ArrayList<Ship> ships;
    private final SeaMap map;
    private boolean running;


    public GameInstance() {
        this.running = true;
        this.ships = new ArrayList<>();
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
                ships.forEach(ship -> ship.update(map));
            }
        }

    }

    @Override
    public void startGame() {
        Thread gameInstanceThread = new Thread(this::processGameLoop);
        gameInstanceThread.start();
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public Ship join(String shipId, String name) {
        Ship ship = new Ship(Vector.ZERO, Vector.ZERO, Direction.BOT, name, 10000, 10000);
        this.ships.add(ship);
        return ship;
    }

    @Override
    public Ship updateDirection(Direction direction) {
        return ships.get(0).updateDirection(GAME_TICK, direction);
    }

    @Override
    public Ship stop() {
        return ships.get(0).stop();
    }
}
