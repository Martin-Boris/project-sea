package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Direction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.WebSocketConnection;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class GameControllerTest {


    @Nested
    class onMessage {
        void caseJOIN() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "JOIN;Test";
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).join("Test");
        }

        void caseLeave() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "LEAVE;Test";
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).leave("Test");
        }

        void caseStop() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "STOP;Test";
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).stop("Test");
        }

        void caseUpdateDirection() {
            WebSocketConnection connection = Mockito.mock(WebSocketConnection.class);
            GameInstanceService gameInstanceService = Mockito.mock(GameInstanceService.class);
            String message = "TURN;LEFT;Test";
            GameController gameController = new GameController(connection, gameInstanceService);
            String responseMessage = gameController.onMessage(message);
            Mockito.verify(gameInstanceService, Mockito.times(1)).updateDirection(Direction.LEFT, "Test");
        }

    }

}
