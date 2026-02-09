package com.bmrt.projectsea.domain;

import java.util.Collection;

public interface NpcBehavior {
    void decideTick(Ship npc, Collection<Ship> allShips, SeaMap map, float gameTick);
}
