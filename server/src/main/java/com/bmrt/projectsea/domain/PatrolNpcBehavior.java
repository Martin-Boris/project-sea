package com.bmrt.projectsea.domain;

import java.util.Collection;

public class PatrolNpcBehavior implements NpcBehavior {

    @Override
    public void decideTick(Ship npc, Collection<Ship> allShips, SeaMap map, float gameTick) {
        if (npc.getSpeed().equals(Vector.ZERO)) {
            npc.updateDirection(gameTick, Direction.RIGHT);
            return;
        }

        Vector nextPosition = npc.getPosition().add(npc.getSpeed());
        if (map.isOut(nextPosition)) {
            Direction reversed = reverse(npc.getDirection());
            npc.updateDirection(gameTick, reversed);
        }
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
