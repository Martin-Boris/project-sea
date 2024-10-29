package com.bmrt.projectsea.websocket.websocket.mapper;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.ShipBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageMapperTest {

    @Test
    void caseToMessage() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.BOT)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .build();
        String message = new MessageMapper().toMessage(ship);
        Assertions.assertEquals(message, "10.0;10.0;0.0;10.0;BOT;Test;10000.0;10000.0");
    }

}
