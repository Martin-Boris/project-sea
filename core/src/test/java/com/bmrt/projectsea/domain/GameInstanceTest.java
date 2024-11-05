package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.websocket.Action;
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

    @Test
    void caseJOINAction() {
        WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
        RenderPort renderPort = Mockito.mock(RenderPort.class);
        ActionCommand cmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.JOIN)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(1.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Ship ship = ShipBuilder
            .newShip()
            .withPosition(10, 11)
            .withSpeed(0, 1)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort);
        gameInstance.handleAction(cmd);
        Assertions.assertTrue(gameInstance.contains("Test"));
        Assertions.assertEquals(gameInstance.get("Test"), ship);
    }

    @Test
    void caseLEAVEAction() {
        WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
        RenderPort renderPort = Mockito.mock(RenderPort.class);
        ActionCommand cmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.LEAVE)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(1.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Ship ship = ShipBuilder
            .newShip()
            .withPosition(10, 11)
            .withSpeed(0, 1)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort);
        gameInstance.addShip(ship);
        gameInstance.handleAction(cmd);
        Assertions.assertFalse(gameInstance.contains("Test"));
    }

    @Test
    void caseSTOPAction() {
        WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
        RenderPort renderPort = Mockito.mock(RenderPort.class);
        ActionCommand cmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.STOP)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(0.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Ship ship = ShipBuilder
            .newShip()
            .withPosition(10, 11)
            .withSpeed(0, 1)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort);
        gameInstance.addShip(ship);
        gameInstance.handleAction(cmd);

        Ship expectedShip = ShipBuilder
            .newShip()
            .withPosition(10, 11)
            .withSpeed(0, 0)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Assertions.assertTrue(gameInstance.contains("Test"));
        Assertions.assertEquals(gameInstance.get("Test"), expectedShip);
    }

    @Test
    void caseTURNAction() {
        WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
        RenderPort renderPort = Mockito.mock(RenderPort.class);
        ActionCommand cmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.TURN)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(-1.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Ship ship = ShipBuilder
            .newShip()
            .withPosition(10, 11)
            .withSpeed(0, 1)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.TOP)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort);
        gameInstance.addShip(ship);
        gameInstance.handleAction(cmd);

        Ship expectedShip = ShipBuilder
            .newShip()
            .withPosition(10, 11)
            .withSpeed(0, -1)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Assertions.assertTrue(gameInstance.contains("Test"));
        Assertions.assertEquals(gameInstance.get("Test"), expectedShip);
    }

}
