package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;

public class WebsocketController {

    private final WebSocket socket;
    private final Ship myShip;

    public WebsocketController(Ship myShip) {
        socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("127.0.0.1", 8080));
        socket.setSendGracefully(true);
        socket.addListener(new WebSocketAdapter(myShip));
        socket.connect();
        this.myShip = myShip;
    }

    public void updateDirection(Direction direction) {
        socket.send(Action.TURN + ";" + direction);
    }


    public void closeConnection() {
        WebSockets.closeGracefully(socket);
    }

    public void stop() {
        socket.send(Action.STOP + ";");
    }
}
