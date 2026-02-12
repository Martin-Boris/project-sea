package com.bmrt.projectsea.domain;

import java.util.Optional;

public interface NpcBehavior {
    Optional<Direction> getNewDirection(Ship npc, SeaMap map);
}
