package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.ShipBuilder;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.WebSocketConnection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class GameControllerTest {


    @Nested
    class onMessage {
        @Test
        void caseJOIN() {
            WebSocketConnectionMock connection = new WebSocketConnectionMock();
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
            GameController gameController = new GameController(connection, gameInstanceService);
            Mockito.when(gameInstanceService.join("Test", 0, 0, gameController)).thenReturn(ship);
            gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).join("Test", 0, 0, gameController);
        }

        @Test
        void caseLeave() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            WebSocketConnection.BroadcastSender sender = Mockito.mock(WebSocketConnection.BroadcastSender.class);
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
            GameController gameController = new GameController(connection, gameInstanceService);
            Mockito.when(gameInstanceService.leave("Test", gameController)).thenReturn(ship);
            Mockito.when(connection.broadcast()).thenReturn(sender);
            gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).leave("Test", gameController);
        }

        @Test
        void caseStop() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            WebSocketConnection.BroadcastSender sender = Mockito.mock(WebSocketConnection.BroadcastSender.class);
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
            GameController gameController = new GameController(connection, gameInstanceService);
            Mockito.when(gameInstanceService.stop("Test", gameController)).thenReturn(ship);
            Mockito.when(connection.broadcast()).thenReturn(sender);
            gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).stop("Test", gameController);
        }

        @Test
        void caseUpdateDirection() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            WebSocketConnection.BroadcastSender sender = Mockito.mock(WebSocketConnection.BroadcastSender.class);
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
            GameController gameController = new GameController(connection, gameInstanceService);
            Mockito.when(gameInstanceService.updateDirection(Direction.LEFT, "Test", gameController)).thenReturn(ship);
            Mockito.when(connection.broadcast()).thenReturn(sender);
            gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).updateDirection(Direction.LEFT, "Test",
                gameController);
        }

        @Test
        void caseShoot() throws InvalidTarget, TargetToFar {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            WebSocketConnection.BroadcastSender sender = Mockito.mock(WebSocketConnection.BroadcastSender.class);
            String message = "SHOOT;Test;Target";
            Ship shipTargeted = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.LEFT)
                .withName("Target")
                .withHealthPoint(4)
                .withMaxHealthPoint(5)
                .build();
            GameController gameController = new GameController(connection, gameInstanceService);
            Mockito.when(gameInstanceService.shoot("Test", "Target", gameController)).thenReturn(shipTargeted);
            Mockito.when(connection.broadcast()).thenReturn(sender);
            gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).shoot("Test", "Target", gameController);
        }

    }

}
