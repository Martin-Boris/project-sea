package com.bmrt.projectsea.websocket.websocket.mapper;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.ShipBuilder;
import com.bmrt.projectsea.websocket.websocket.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageMapperTest {

    @Test
    void caseJOIN() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.BOT)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .build();
        String message = new MessageMapper().toMessage(Action.valueOf("JOIN"), ship);
        Assertions.assertEquals(message, "JOIN;10.0;10.0;0.0;0.0;BOT;Test;10000.0;10000.0");
    }

    @Test
    void caseTURN() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.BOT)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .build();
        String message = new MessageMapper().toMessage(Action.TURN, ship);
        Assertions.assertEquals(message, "TURN;10.0;10.0;0.0;0.0;BOT;Test;10000.0;10000.0");
    }

    @Test
    void caseLEAVE() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.BOT)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .build();
        String message = new MessageMapper().toMessage(Action.LEAVE, ship);
        Assertions.assertEquals(message, "LEAVE;10.0;10.0;0.0;0.0;BOT;Test;10000.0;10000.0");
    }

    @Test
    void caseSTOP() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.BOT)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .build();
        String message = new MessageMapper().toMessage(Action.STOP, ship);
        Assertions.assertEquals(message, "STOP;10.0;10.0;0.0;0.0;BOT;Test;10000.0;10000.0");
    }

}
