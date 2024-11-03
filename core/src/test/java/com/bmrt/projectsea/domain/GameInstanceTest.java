package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameInstanceTest {


    @Test
    void caseAddShip() {
        WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
        RenderPort renderPort = Mockito.mock(RenderPort.class);
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.TOP)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort);
        gameInstance.addShip(ship);
        Assertions.assertEquals(gameInstance.get("Test"), ship);
    }

}
