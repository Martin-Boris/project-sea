package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.GameInstance;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;

public class WebSocketListenerAdapter implements WebSocketListener {

    private final GameInstance gameInstance;
    private final ShipMapper shipMapper;

    public WebSocketListenerAdapter(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        this.shipMapper = new ShipMapper();
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        webSocket.send(Action.JOIN + ";" + gameInstance.getMyShipName());
        return FULLY_HANDLED;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        webSocket.send(Action.LEAVE + ";" + gameInstance.getMyShipName());
        return FULLY_HANDLED;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        String[] data = packet.split(";");
        if (gameInstance.contains(data[5])) {
            shipMapper.updateShip(data, gameInstance.get(data[5]));
        } else {
            gameInstance.addShip(shipMapper.createShip(data));
        }
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
