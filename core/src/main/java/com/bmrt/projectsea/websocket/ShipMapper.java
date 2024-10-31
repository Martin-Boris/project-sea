package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;

public class ShipMapper {

    public void updateShip(String[] data, Ship ship) {
        ship.setPosition(Float.parseFloat(data[0]), Float.parseFloat(data[1]));
        ship.setSpeed(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
        ship.setDirection(Direction.valueOf(data[4]));
        ship.setHealthPoint(Float.parseFloat(data[6]));
        ship.setMaxHealthPoint(Float.parseFloat(data[7]));
    }
}
