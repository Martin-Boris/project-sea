package com.bmrt.projectsea.websocket.websocket.mapper;

import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.Ship;

public class MessageMapper {

    public String toMessage(Action action, Ship ship) {
        return action + ";"
            + ship.getPosition().getX() + ";"
            + ship.getPosition().getY() + ";"
            + ship.getSpeed().getX() + ";"
            + ship.getSpeed().getY() + ";"
            + ship.getDirection() + ";"
            + ship.getName() + ";"
            + ship.getHealthPoint() + ";"
            + ship.getMaxHealthPoint();
    }

    public String toShootMessage(Action action, Ship target, Ship shooter) {
        return action + ";"
            + target.getName() + ";"
            + target.getHealthPoint() + ";"
            + target.getMaxHealthPoint() + ";"
            + shooter.getName();
    }
}
