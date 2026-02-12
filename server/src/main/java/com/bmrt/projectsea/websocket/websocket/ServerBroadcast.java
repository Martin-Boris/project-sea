package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.ClientCommunicationPort;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.websocket.websocket.mapper.MessageMapper;
import io.quarkus.websockets.next.OpenConnections;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ServerBroadcast implements ClientCommunicationPort {

    private final OpenConnections openConnections;
    private final MessageMapper mapper = new MessageMapper();

    @Inject
    public ServerBroadcast(OpenConnections openConnections) {
        this.openConnections = openConnections;
    }

    @Override
    public void sendToAllPLayer(Action action, Ship ship) {
        String message = mapper.toMessage(action, ship);
        for (WebSocketConnection webSocketConnection : openConnections.listAll()) {
            webSocketConnection.sendText(message)
                .subscribe()
                .with(
                    unused -> {
                    },
                    failure -> System.out.println("Failed to send to " + webSocketConnection.id())
                );
        }
    }
}
