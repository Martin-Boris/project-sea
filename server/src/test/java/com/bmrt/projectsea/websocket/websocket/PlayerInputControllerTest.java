package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.WebSocketConnection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class PlayerInputControllerTest {

    @Nested
    class onMessage {
        @Test
        void caseJOIN() {
            WebSocketConnectionMock connection = new WebSocketConnectionMock();
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "JOIN;Test";
            PlayerInputController playerInputController = new PlayerInputController(connection, gameInstanceService);
            playerInputController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).join("Test", 0, 0);
        }

        @Test
        void caseStop() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "STOP;Test";
            PlayerInputController playerInputController = new PlayerInputController(connection, gameInstanceService);
            playerInputController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).stop("Test");
        }

        @Test
        void caseUpdateDirection() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "TURN;LEFT;Test";
            PlayerInputController playerInputController = new PlayerInputController(connection, gameInstanceService);
            playerInputController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).updateDirection(Direction.LEFT, "Test");
        }

        @Test
        void caseShoot() throws InvalidTarget, TargetToFar {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "SHOOT;Test;Target";
            PlayerInputController playerInputController = new PlayerInputController(connection, gameInstanceService);
            playerInputController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).shoot("Test", "Target");
        }

    }

}
