package com.bmrt.projectsea.teavm.websocket;

import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketState;
import com.github.czyzby.websocket.serialization.Serializer;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * TeaVM implementation of the gdx-websockets WebSocket interface.
 * Uses the browser's native WebSocket API through JSO bindings.
 */
public class TeaVMWebSocket implements WebSocket {

    private final String url;
    private JSWebSocket jsWebSocket;
    private final List<WebSocketListener> listeners = new ArrayList<>();
    private boolean sendGracefully = false;
    private Serializer serializer;

    public TeaVMWebSocket(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public WebSocketState getState() {
        if (jsWebSocket == null) {
            return WebSocketState.CLOSED;
        }
        switch (jsWebSocket.getReadyState()) {
            case JSWebSocket.CONNECTING:
                return WebSocketState.CONNECTING;
            case JSWebSocket.OPEN:
                return WebSocketState.OPEN;
            case JSWebSocket.CLOSING:
                return WebSocketState.CLOSING;
            case JSWebSocket.CLOSED:
            default:
                return WebSocketState.CLOSED;
        }
    }

    @Override
    public boolean isOpen() {
        return getState() == WebSocketState.OPEN;
    }

    @Override
    public boolean isClosed() {
        return getState() == WebSocketState.CLOSED;
    }

    @Override
    public boolean isConnecting() {
        return getState() == WebSocketState.CONNECTING;
    }

    @Override
    public boolean isClosing() {
        return getState() == WebSocketState.CLOSING;
    }

    @Override
    public boolean isSecure() {
        return url != null && url.startsWith("wss://");
    }

    @Override
    public void connect() {
        jsWebSocket = JSWebSocket.create(url);
        setupListeners();
    }

    private void setupListeners() {
        jsWebSocket.setOnOpen(new EventListener<Event>() {
            @Override
            public void handleEvent(Event evt) {
                for (WebSocketListener listener : listeners) {
                    if (listener.onOpen(TeaVMWebSocket.this)) {
                        break;
                    }
                }
            }
        });

        jsWebSocket.setOnClose(new EventListener<CloseEvent>() {
            @Override
            public void handleEvent(CloseEvent evt) {
                for (WebSocketListener listener : listeners) {
                    if (listener.onClose(TeaVMWebSocket.this, evt.getCode(), evt.getReason())) {
                        break;
                    }
                }
            }
        });

        jsWebSocket.setOnMessage(new EventListener<MessageEvent>() {
            @Override
            public void handleEvent(MessageEvent evt) {
                Object data = evt.getData();
                if (data instanceof String) {
                    for (WebSocketListener listener : listeners) {
                        if (listener.onMessage(TeaVMWebSocket.this, (String) data)) {
                            break;
                        }
                    }
                }
            }
        });

        jsWebSocket.setOnError(new EventListener<Event>() {
            @Override
            public void handleEvent(Event evt) {
                Exception error = new Exception("WebSocket error occurred");
                for (WebSocketListener listener : listeners) {
                    if (listener.onError(TeaVMWebSocket.this, error)) {
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void close() {
        if (jsWebSocket != null) {
            jsWebSocket.close();
        }
    }

    @Override
    public void close(int code, String reason) {
        if (jsWebSocket != null) {
            jsWebSocket.close(code, reason);
        }
    }

    @Override
    public void send(String message) {
        if (isOpen()) {
            jsWebSocket.send(message);
        } else if (!sendGracefully) {
            throw new IllegalStateException("WebSocket is not open. State: " + getState());
        }
    }

    @Override
    public void send(byte[] message) {
        if (isOpen()) {
            jsWebSocket.send(message);
        } else if (!sendGracefully) {
            throw new IllegalStateException("WebSocket is not open. State: " + getState());
        }
    }

    @Override
    public void send(Object packet) {
        if (packet instanceof String) {
            send((String) packet);
        } else if (packet instanceof byte[]) {
            send((byte[]) packet);
        } else {
            send(packet.toString());
        }
    }

    @Override
    public void addListener(WebSocketListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(WebSocketListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void setSendGracefully(boolean sendGracefully) {
        this.sendGracefully = sendGracefully;
    }

    @Override
    public boolean isSendGracefully() {
        return sendGracefully;
    }

    @Override
    public void setUseTcpNoDelay(boolean useTcpNoDelay) {
        // Not applicable for browser WebSockets
    }

    @Override
    public boolean isUseTcpNoDelay() {
        return false;
    }

    @Override
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Serializer getSerializer() {
        return serializer;
    }
}
