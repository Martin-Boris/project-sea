package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;

public class ShipMapper {

    public Ship updateShip(String message, Ship ship) {
        String[] data = message.split(";");
        ship.setPosition(Float.parseFloat(data[0]), Float.parseFloat(data[1]));
        ship.setSpeed(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
        ship.setDirection(Direction.valueOf(data[4]));
        ship.setName(data[5]);
        ship.setHealthPoint(Float.parseFloat(data[6]));
        ship.setMaxHealthPoint(Float.parseFloat(data[7]));
        return ship;
    }
}
