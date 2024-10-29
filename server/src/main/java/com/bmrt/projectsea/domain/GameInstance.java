package com.bmrt.projectsea.domain;

import java.time.Clock;
import java.util.ArrayList;

public class GameInstance implements GameActionApi {

    public static final float GAME_TICK = 1 / 60f * 1000f;
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
        long previousTickTime = clock.millis();
        while (running) {
            currentTime = clock.millis();
            dt = currentTime - previousTickTime;
            if (dt >= GAME_TICK) {
                ships.forEach(ship -> ship.update(map));
            }
        }

    }

    public void start() {
        Thread gameInstanceThread = new Thread(this::processGameLoop);
        gameInstanceThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public Ship join(String shipId, String name) {
        if (ships.isEmpty()) {
            start();
        }
        Ship ship = new Ship(Vector.ZERO, Vector.ZERO, Direction.BOT, name, 10000, 10000);
        this.ships.add(ship);
        return ship;
    }
}
