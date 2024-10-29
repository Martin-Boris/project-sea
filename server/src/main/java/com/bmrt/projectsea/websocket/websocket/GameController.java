package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.websocket.websocket.mapper.MessageMapper;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Inject;

@WebSocket(path = "/{shipId}")
public class GameController {

    private final MessageMapper mapper = new MessageMapper();
    @Inject
    private WebSocketConnection connection;
    @Inject
    private GameInstanceService gameInstanceService;

    @OnOpen()
    public void onOpen() {
    }

    @OnTextMessage(broadcast = true)
    public String onMessage(String name) {
        Ship ship = gameInstanceService.join(connection.pathParam("shipId"), name);
        return mapper.toMessage(ship);
    }


}
