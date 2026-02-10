package com.bmrt.projectsea.domain;

import java.util.Collection;

public class PatrolNpcBehavior implements NpcBehavior {

    private static final float RANDOM_TURN_CHANCE = 0.005f;
    private final RandomProvider random;

    public PatrolNpcBehavior(RandomProvider random) {
        this.random = random;
    }

    @Override
    public boolean decideTick(Ship npc, Collection<Ship> allShips, SeaMap map, float gameTick) {
        if (npc.getSpeed().equals(Vector.ZERO)) {
            npc.updateDirection(gameTick, Direction.RIGHT);
            return true;
        }

        if (npc.isOutNextTick(map)) {
            npc.updateDirection(gameTick, npc.getDirection().opposite());
            return true;
        }

        if (random.nextFloat() <= RANDOM_TURN_CHANCE) {
            npc.updateDirection(gameTick, random.nextAmong(Direction.nonOppositeOf(npc.getDirection())));
            return true;
        }

        return false;
    }
}
