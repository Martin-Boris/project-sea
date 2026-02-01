package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.WebSocketPort;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;

public class WebsocketController implements WebSocketPort {

    private final String shipName;
    private final WebSocket socket;


    public WebsocketController(String shipName, String url, int port, String protocol, String path) {
        this.shipName = shipName;
        String fullUrl;
        if ("wss".equals(protocol)) {
            fullUrl = WebSockets.toSecureWebSocketUrl(url,
                port,
                path);
        } else {
            fullUrl = WebSockets.toWebSocketUrl(url,
                port,
                path);
        }
        socket = WebSockets.newSocket(fullUrl);
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
        } catch (Exception ignored) {
        }
    }

}
