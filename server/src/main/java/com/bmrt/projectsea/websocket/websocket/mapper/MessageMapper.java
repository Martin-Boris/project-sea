package com.bmrt.projectsea.websocket.websocket.mapper;

import com.bmrt.projectsea.domain.Ship;

public class MessageMapper {

    public String toMessage(Ship ship) {
        return ship.getPosition().getX() + ";"
            + ship.getPosition().getY() + ";"
            + ship.getSpeed().getX() + ";"
            + ship.getPosition().getY() + ";"
            + ship.getDirection() + ";"
            + ship.getName() + ";"
            + ship.getHealthPoint() + ";"
            + ship.getMaxHealthPoint();
    }
}
