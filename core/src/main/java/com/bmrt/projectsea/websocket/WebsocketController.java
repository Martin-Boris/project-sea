package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.WebSocketPort;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;

public class WebsocketController implements WebSocketPort {

    private final String shipName;
    private final WebSocket socket;


    public WebsocketController(String shipName) {
        this.shipName = shipName;
        socket = WebSockets.newSocket(WebSockets.toSecureWebSocketUrl("project-sea.bmrt.fr", 443, "/server"));
    }

    @Override
    public void updateDirection(Direction direction) {
        socket.send(Action.TURN + ";" + direction + ";" + shipName);
    }


    @Override
    public void stop() {
        socket.send(Action.STOP + ";" + shipName);
    }

    @Override
    public void shoot(String shooter, String target) {
        socket.send(Action.SHOOT + ";" + shooter + ";" + target);
    }

    public void closeConnection() {
        WebSockets.closeGracefully(socket);
    }

    @Override
    public void addListener(GameInstance gameInstance) {
        socket.addListener(new WebSocketListenerAdapter(gameInstance));
    }

    @Override
    public void startConnection() {
        socket.setSendGracefully(true);
        try {
            socket.connect();
        } catch (Exception e) {
            String test = "toto";
        }
    }

}
