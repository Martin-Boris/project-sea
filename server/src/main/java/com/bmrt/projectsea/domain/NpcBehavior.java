package com.bmrt.projectsea.domain;

import java.util.Collection;

public interface NpcBehavior {
    boolean decideTick(Ship npc, Collection<Ship> allShips, SeaMap map, float gameTick);
}
