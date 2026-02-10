package com.bmrt.projectsea.domain;

import java.util.Collection;

public class PatrolNpcBehavior implements NpcBehavior {

    @Override
    public boolean decideTick(Ship npc, Collection<Ship> allShips, SeaMap map, float gameTick) {
        if (npc.getSpeed().equals(Vector.ZERO)) {
            npc.updateDirection(gameTick, Direction.RIGHT);
            return true;
        }

        if (npc.isOutNextTick(map)) {
            Direction reversed = reverse(npc.getDirection());
            npc.updateDirection(gameTick, reversed);
            return true;
        }

        return false;
    }

    private Direction reverse(Direction direction) {
        switch (direction) {
            case RIGHT:
                return Direction.LEFT;
            case LEFT:
                return Direction.RIGHT;
            case TOP:
                return Direction.BOT;
            default:
                return Direction.TOP;
        }
    }
}
