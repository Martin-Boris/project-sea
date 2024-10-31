package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameInstance;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;

public class WebsocketController {

    private final WebSocket socket;

    private final GameInstance gameInstance;

    public WebsocketController(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("127.0.0.1", 8080));
        socket.setSendGracefully(true);
        socket.addListener(new WebSocketAdapter(gameInstance));
        socket.connect();
    }

    public void updateDirection(Direction direction) {
        socket.send(Action.TURN + ";" + direction + ";" + gameInstance.getMyShip().getName());
    }


    public void stop() {
        socket.send(Action.STOP + ";" + gameInstance.getMyShip().getName());
    }

    public void closeConnection() {
        WebSockets.closeGracefully(socket);
    }
}
