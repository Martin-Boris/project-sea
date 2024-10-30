package com.bmrt.projectsea.domain;

import java.time.Clock;
import java.util.ArrayList;

public class GameInstance implements GameActionApi {

    public static final float GAME_TICK = 1 / 60f;
    private final ArrayList<Ship> ships;
    private final SeaMap map;
    private final Clock clock;
    private boolean running;


    public GameInstance(Clock clock) {
        this.running = true;
        this.ships = new ArrayList<>();
        this.map = new SeaMap(20, 20);
        this.clock = clock;
    }

    private void processGameLoop() {
        long currentTime;
        long dt;
        long previousTickTime = clock.instant().getEpochSecond();
        //long previousTickTime = System.nanoTime();
        float accumulator = 0;
        while (running) {
            currentTime = clock.instant().getEpochSecond();
            //currentTime = System.nanoTime();
            //dt = (currentTime - previousTickTime) / 1000000000;
            dt = currentTime - previousTickTime;
            previousTickTime = currentTime;
            accumulator += dt;
            if (accumulator >= GAME_TICK) {
                ships.forEach(ship -> ship.update(map));
                accumulator -= GAME_TICK;
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
