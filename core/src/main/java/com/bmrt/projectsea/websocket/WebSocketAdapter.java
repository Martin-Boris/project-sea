package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Ship;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;

public class WebSocketAdapter implements WebSocketListener {

    private final Ship myShip;
    private final ShipMapper shipMapper;

    public WebSocketAdapter(Ship myShip) {
        this.myShip = myShip;
        this.shipMapper = new ShipMapper();
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        webSocket.send(Action.JOIN + ";" + "Torred");
        return FULLY_HANDLED;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        shipMapper.updateShip(packet, myShip);
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
