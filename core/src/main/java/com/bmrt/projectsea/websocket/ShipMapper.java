package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;

public class ShipMapper {

    public void updateShip(String[] data, Ship ship) {
        ship.setPosition(Float.parseFloat(data[0]), Float.parseFloat(data[1]));
        ship.setSpeed(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
        ship.setDirection(Direction.valueOf(data[4]));
        ship.setHealthPoint(Float.parseFloat(data[6]));
        ship.setMaxHealthPoint(Float.parseFloat(data[7]));
    }

    public Ship createShip(String[] data) {
        return new Ship(
            new Vector(Float.parseFloat(data[0]), Float.parseFloat(data[1])),
            new Vector(Float.parseFloat(data[2]), Float.parseFloat(data[3])),
            Direction.valueOf(data[4]),
            data[5],
            Float.parseFloat(data[6]),
            Float.parseFloat(data[7])
        );
    }
}
