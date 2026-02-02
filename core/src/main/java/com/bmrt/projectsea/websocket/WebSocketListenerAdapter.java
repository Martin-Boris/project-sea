package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.GameInstance;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;

public class WebSocketListenerAdapter implements WebSocketListener {

    private final GameInstance gameInstance;
    private final CommandMapper commandMapper;

    public WebSocketListenerAdapter(GameInstance gameInstance, CommandMapper commandMapper) {
        this.gameInstance = gameInstance;
        this.commandMapper = commandMapper;
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        webSocket.send(Action.JOIN + ";" + gameInstance.getMyShipName());
        return FULLY_HANDLED;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        //TODO refactor : create command for all case LEAVE TURN and JOIN and do logic inside GameInstance (for now
        // create command each time pool later
        String[] data = packet.split(";");
        if (Action.SHOOT.name().equals(data[0])) {
            gameInstance.renderShoot(commandMapper.getShootCommand(data));
        } else {
            gameInstance.handleAction(commandMapper.getCommand(data));
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
