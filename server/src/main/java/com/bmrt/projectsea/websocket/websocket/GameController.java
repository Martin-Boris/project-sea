package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.ClientCommunicationPort;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import com.bmrt.projectsea.websocket.websocket.mapper.MessageMapper;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.UserData;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;

import java.util.Collection;

@WebSocket(path = "/")
public class GameController implements ClientCommunicationPort {

    public static final UserData.TypedKey<String> SHIP_ID = UserData.TypedKey.forString("shipId");
    private final MessageMapper mapper = new MessageMapper();
    private final WebSocketConnection connection;
    private final GameInstanceService gameInstanceService;

    public GameController(WebSocketConnection connection, GameInstanceService gameInstanceService) {
        this.connection = connection;
        this.gameInstanceService = gameInstanceService;
    }

    @OnOpen()
    public void onOpen() {
        Collection<Ship> ships = gameInstanceService.getShips();
        ships.forEach(ship -> connection.sendTextAndAwait(mapper.toMessage(Action.JOIN, ship)));
    }

    @OnClose()
    public void onClose(WebSocketConnection socket) {
        gameInstanceService.leave(socket.userData().get(SHIP_ID), this);
    }

    @OnTextMessage()
    public void onMessage(String message) {
        //TODO handle invalid index
        // custom message following action (only leave simple)
        String[] action = message.split(";");
        if (action[0].equals(Action.JOIN.name())) {
            gameInstanceService.join(action[1], 0, 0, this);
            connection.userData().put(SHIP_ID, action[1]);
        } else if (action[0].equals(Action.TURN.name())) {
            gameInstanceService.updateDirection(Direction.valueOf(action[1]), action[2], this);
        } else if (action[0].equals(Action.SHOOT.name())) {
            try {
                gameInstanceService.shoot(action[1], action[2], this);
            } catch (TargetToFar | InvalidTarget ignored) {
            }
        } else {
            gameInstanceService.stop(action[1], this);
        }
    }


    @Override
    public void sendToAllPLayer(Action action, Ship ship) {
        connection.broadcast().sendTextAndAwait(mapper.toMessage(action, ship));
    }
}
