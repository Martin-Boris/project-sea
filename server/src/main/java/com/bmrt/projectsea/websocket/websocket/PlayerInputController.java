package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.application.GameInstanceService;
import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import com.bmrt.projectsea.websocket.websocket.mapper.MessageMapper;
import io.quarkus.websockets.next.*;

import java.util.Collection;

@WebSocket(path = "/")
public class PlayerInputController {

    public static final UserData.TypedKey<String> SHIP_ID = UserData.TypedKey.forString("shipId");
    private final MessageMapper mapper = new MessageMapper();
    private final WebSocketConnection connection;
    private final GameInstanceService gameInstanceService;

    public PlayerInputController(WebSocketConnection connection, GameInstanceService gameInstanceService) {
        this.connection = connection;
        this.gameInstanceService = gameInstanceService;
    }

    @OnOpen()
    public void onOpen() {
        Collection<Ship> ships = gameInstanceService.getShips();
        for (Ship ship : ships) {
            connection.sendText(mapper.toMessage(Action.JOIN, ship)).subscribe().with(
                unused -> {
                },
                failure -> System.out.println("Failed to send ship " + ship.getName())
            );
        }
    }

    @OnClose()
    public void onClose(WebSocketConnection socket) {
        gameInstanceService.leave(socket.userData().get(SHIP_ID));
    }

    @OnTextMessage()
    public void onMessage(String message) {
        //TODO handle invalid index
        // custom message following action (only leave simple)
        String[] action = message.split(";");
        if (action[0].equals(Action.JOIN.name())) {
            gameInstanceService.join(action[1], 0, 0);
            connection.userData().put(SHIP_ID, action[1]);
        } else if (action[0].equals(Action.TURN.name())) {
            gameInstanceService.updateDirection(Direction.valueOf(action[1]), action[2]);
        } else if (action[0].equals(Action.SHOOT.name())) {
            try {
                gameInstanceService.shoot(action[1], action[2]);
            } catch (TargetToFar | InvalidTarget ignored) {
            }
        } else {
            gameInstanceService.stop(action[1]);
        }
    }
}
