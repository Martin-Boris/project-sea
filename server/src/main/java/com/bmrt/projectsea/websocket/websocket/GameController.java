package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.websocket.websocket.mapper.MessageMapper;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Inject;

@WebSocket(path = "/")
public class GameController {

    private final MessageMapper mapper = new MessageMapper();
    @Inject
    private WebSocketConnection connection;
    @Inject
    private GameInstanceService gameInstanceService;

    @OnOpen()
    public void onOpen() {
        gameInstanceService.startGame();
    }

    @OnClose()
    public void onClose() {
        gameInstanceService.stopGame();
    }

    @OnTextMessage(broadcast = true)
    public String onMessage(String message) {
        String[] action = message.split(";");
        Ship ship;
        if (action[0].equals(Action.JOIN.name())) {
            ship = gameInstanceService.join("1", action[1]);
        } else if (action[0].equals(Action.TURN.name())) {
            ship = gameInstanceService.updateDirection(Direction.valueOf(action[1]));
        } else {
            ship = gameInstanceService.stop();
        }
        return mapper.toMessage(ship);
    }


}
