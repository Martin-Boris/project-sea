package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.GameInstance;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;

public class WebSocketAdapter implements WebSocketListener {

    private final GameInstance gameInstance;
    private final ShipMapper shipMapper;

    public WebSocketAdapter(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        this.shipMapper = new ShipMapper();
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        webSocket.send(Action.JOIN + ";" + gameInstance.getMyShip());
        return FULLY_HANDLED;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        shipMapper.updateShip(packet, gameInstance.getMyShip());
        return FULLY_HANDLED;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        return false;
    }
}
