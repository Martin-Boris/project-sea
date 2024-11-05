package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.GameInstance;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;

public class WebSocketListenerAdapter implements WebSocketListener {

    private final GameInstance gameInstance;
    private final CommandMapper commandMapper;

    public WebSocketListenerAdapter(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        this.commandMapper = new CommandMapper();
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
        //TODO refactor : create command for all case LEAVE TURN and JOIN and do logic inside GameInstance (for now
        // create command each time pool later
        gameInstance.handleAction(commandMapper.getCommand(packet));
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
