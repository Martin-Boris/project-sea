package com.bmrt.projectsea.websocket.websocket;

import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.HandshakeRequest;
import io.quarkus.websockets.next.UserData;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketConnectionMock implements WebSocketConnection {

    private final BroadcastSender broadcastSenderMock = Mockito.mock(BroadcastSender.class);

    @Override
    public String endpointId() {
        return null;
    }

    @Override
    public BroadcastSender broadcast() {
        return broadcastSenderMock;
    }

    public BroadcastSender getBroadcastSenderMock() {
        return broadcastSenderMock;
    }

    @Override
    public Set<WebSocketConnection> getOpenConnections() {
        return null;
    }

    @Override
    public String subprotocol() {
        return null;
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public String pathParam(String name) {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public CloseReason closeReason() {
        return null;
    }

    @Override
    public Uni<Void> close(CloseReason reason) {
        return null;
    }

    @Override
    public HandshakeRequest handshakeRequest() {
        return null;
    }

    @Override
    public Instant creationTime() {
        return null;
    }

    @Override
    public UserData userData() {
        return new UserData() {
            private final ConcurrentMap<String, Object> data = new ConcurrentHashMap();

            public <VALUE> VALUE get(UserData.TypedKey<VALUE> key) {
                return null;
            }

            public <VALUE> VALUE put(UserData.TypedKey<VALUE> key, VALUE value) {
                return null;
            }

            public <VALUE> VALUE remove(UserData.TypedKey<VALUE> key) {
                return null;
            }

            public void clear() {
                this.data.clear();
            }

            public int size() {
                return this.data.size();
            }
        };
    }

    @Override
    public Uni<Void> sendText(String message) {
        return null;
    }

    @Override
    public <M> Uni<Void> sendText(M message) {
        return null;
    }

    @Override
    public Uni<Void> sendBinary(Buffer message) {
        return null;
    }

    @Override
    public Uni<Void> sendPing(Buffer data) {
        return null;
    }

    @Override
    public Uni<Void> sendPong(Buffer data) {
        return null;
    }
}


