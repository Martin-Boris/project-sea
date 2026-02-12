package com.bmrt.projectsea.infrastructure;

import com.bmrt.projectsea.domain.Tickable;

public class GameLoop {

    public static final float GAME_TICK = 1 / 60f;
    private static final float GAME_TICK_NANO = GAME_TICK * 1_000_000_000;

    private final Tickable tickable;
    private volatile boolean running;
    private Thread gameThread;

    public GameLoop(Tickable tickable) {
        this.tickable = tickable;
        this.running = false;
    }

    public synchronized void start() {
        if (!running) {
            running = true;
            gameThread = new Thread(this::processGameLoop, "GameLoop");
            gameThread.start();
        }
    }

    public synchronized void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    private void processGameLoop() {
        long previousTickTime = System.nanoTime();
        float accumulator = 0;

        while (running) {
            long currentTime = System.nanoTime();
            long dt = currentTime - previousTickTime;
            previousTickTime = currentTime;
            accumulator += dt;

            if (accumulator >= GAME_TICK_NANO) {
                accumulator -= GAME_TICK_NANO;
                tickable.tick(GAME_TICK);
            }
        }
    }
}
