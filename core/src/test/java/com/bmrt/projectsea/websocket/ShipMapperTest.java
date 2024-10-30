package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.ShipBuilder;
import com.bmrt.projectsea.domain.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShipMapperTest {

    @Test
    void caseUpdateShip() {
        String message = "10.0;10.0;10.0;10.0;BOT;Test;10000.0;10000.0";
        Ship ship = ShipBuilder
            .newShip()
            .withName("Toto")
            .withDirection(Direction.TOP)
            .withPosition(1, 0)
            .withSpeed(0, 0)
            .withHealthPoint(5000)
            .withMaxHealthPoint(5000)
            .build();
        new ShipMapper().updateShip(message, ship);
        Assertions.assertEquals(ship, new Ship(new Vector(10, 10), new Vector(10, 10), Direction.BOT, "Test", 10000,
            10000));
    }

}
