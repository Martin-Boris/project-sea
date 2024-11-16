package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import com.bmrt.projectsea.websocket.websocket.mapper.MessageMapper;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;

import java.util.Collection;

@WebSocket(path = "/")
public class GameController {

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

    @OnTextMessage()
    public void onMessage(String message) {
        String[] action = message.split(";");
        //TODO handle invalid index
        // TODO refactor sendText
        if (action[0].equals(Action.JOIN.name())) {
            Ship ship = gameInstanceService.join(action[1], 0, 0);
            connection.broadcast().sendTextAndAwait(mapper.toMessage(Action.valueOf(action[0]), ship));
        } else if (action[0].equals(Action.LEAVE.name())) {
            Ship ship = gameInstanceService.leave(action[1]);
            connection.broadcast().sendTextAndAwait(mapper.toMessage(Action.valueOf(action[0]), ship));
        } else if (action[0].equals(Action.TURN.name())) {
            Ship ship = gameInstanceService.updateDirection(Direction.valueOf(action[1]), action[2]);
            connection.broadcast().sendTextAndAwait(mapper.toMessage(Action.valueOf(action[0]), ship));
        } else if (action[0].equals(Action.SHOOT.name())) {
            try {
                Ship ship = gameInstanceService.shoot(action[1], action[2]);
                connection.broadcast().sendTextAndAwait(mapper.toMessage(Action.valueOf(action[0]), ship));
            } catch (TargetToFar | InvalidTarget ignored) {
            }
        } else {
            Ship ship = gameInstanceService.stop(action[1]);
            connection.broadcast().sendTextAndAwait(mapper.toMessage(Action.valueOf(action[0]), ship));
        }
    }


}
