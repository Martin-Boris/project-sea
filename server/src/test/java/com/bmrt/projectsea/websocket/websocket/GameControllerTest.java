package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.ShipBuilder;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.WebSocketConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class GameControllerTest {


    @Nested
    class onMessage {
        @Test
        void caseJOIN() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "JOIN;Test";
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.TOP)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            Mockito.when(gameInstanceService.join("Test")).thenReturn(ship);
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).join("Test");
            Assertions.assertEquals(responseMessage, "JOIN;10.0;10.0;0.0;0.0;TOP;Test;5.0;5.0");
        }

        @Test
        void caseLeave() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "LEAVE;Test";
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.TOP)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            Mockito.when(gameInstanceService.leave("Test")).thenReturn(ship);
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).leave("Test");
            Assertions.assertEquals(responseMessage, "LEAVE;10.0;10.0;0.0;0.0;TOP;Test;5.0;5.0");
        }

        @Test
        void caseStop() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "STOP;Test";
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.TOP)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            Mockito.when(gameInstanceService.stop("Test")).thenReturn(ship);
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).stop("Test");
            Assertions.assertEquals(responseMessage, "STOP;10.0;10.0;0.0;0.0;TOP;Test;5.0;5.0");
        }

        @Test
        void caseUpdateDirection() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "TURN;LEFT;Test";
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.LEFT)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            Mockito.when(gameInstanceService.updateDirection(Direction.LEFT, "Test")).thenReturn(ship);
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).updateDirection(Direction.LEFT, "Test");
            Assertions.assertEquals(responseMessage, "TURN;10.0;10.0;0.0;0.0;LEFT;Test;5.0;5.0");
        }

        @Test
        void caseShoot() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "SHOOT;Test;Target";
            Ship shipTargeted = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.LEFT)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            Mockito.when(gameInstanceService.shoot("Test", "Target")).thenReturn(shipTargeted);
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).shoot("Test", "Target");
            Assertions.assertEquals(responseMessage, "SHOOT;10.0;10.0;0.0;0.0;LEFT;Test;5.0;5.0");
        }

    }

}
