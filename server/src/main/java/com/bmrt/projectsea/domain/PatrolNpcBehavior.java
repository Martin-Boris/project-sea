package com.bmrt.projectsea.domain;

import java.util.Optional;

public class PatrolNpcBehavior implements NpcBehavior {

    private static final float RANDOM_TURN_CHANCE = 0.005f;
    private final RandomProvider random;

    public PatrolNpcBehavior(RandomProvider random) {
        this.random = random;
    }

    @Override
    public Optional<Direction> getNewDirection(Ship npc, SeaMap map) {
        if (npc.getSpeed().equals(Vector.ZERO)) {
            return Optional.of(Direction.RIGHT);
        }

        if (npc.isOutNextTick(map)) {
            return Optional.of(npc.getDirection().opposite());
        }

        if (random.nextFloat() <= RANDOM_TURN_CHANCE) {
            return Optional.of(random.nextAmong(Direction.nonOppositeOf(npc.getDirection())));
        }

        return Optional.empty();
    }
}
