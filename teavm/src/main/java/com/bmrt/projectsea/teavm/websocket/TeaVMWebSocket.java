package com.bmrt.projectsea.teavm.websocket;

import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.data.WebSocketState;
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
    public WebSocket connect() {
        jsWebSocket = JSWebSocket.create(url);
        setupListeners();
        return this;
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
    public WebSocket close() {
        if (jsWebSocket != null) {
            jsWebSocket.close();
        }
        return this;
    }

    @Override
    public WebSocket close(WebSocketCloseCode code) {
        if (jsWebSocket != null) {
            jsWebSocket.close(code.getCode(), code.name());
        }
        return this;
    }

    @Override
    public WebSocket close(WebSocketCloseCode code, String reason) {
        if (jsWebSocket != null) {
            jsWebSocket.close(code.getCode(), reason);
        }
        return this;
    }

    @Override
    public boolean send(String message) {
        if (isOpen()) {
            jsWebSocket.send(message);
            return true;
        } else if (sendGracefully) {
            return false;
        }
        throw new IllegalStateException("WebSocket is not open. State: " + getState());
    }

    @Override
    public boolean send(byte[] message) {
        if (isOpen()) {
            jsWebSocket.send(message);
            return true;
        } else if (sendGracefully) {
            return false;
        }
        throw new IllegalStateException("WebSocket is not open. State: " + getState());
    }

    @Override
    public WebSocket addListener(WebSocketListener listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public WebSocket removeListener(WebSocketListener listener) {
        listeners.remove(listener);
        return this;
    }

    @Override
    public WebSocket setSendGracefully(boolean sendGracefully) {
        this.sendGracefully = sendGracefully;
        return this;
    }

    @Override
    public boolean isSendGracefully() {
        return sendGracefully;
    }

    @Override
    public WebSocket setUseTcpNoDelay(boolean useTcpNoDelay) {
        // Not applicable for browser WebSockets
        return this;
    }

    @Override
    public boolean isUseTcpNoDelay() {
        return false;
    }

    @Override
    public WebSocket setSerializer(com.github.czyzby.websocket.serialization.Serializer serializer) {
        // Not implemented - using raw string messages
        return this;
    }

    @Override
    public com.github.czyzby.websocket.serialization.Serializer getSerializer() {
        return null;
    }

    @Override
    public boolean send(Object packet) {
        if (packet instanceof String) {
            return send((String) packet);
        } else if (packet instanceof byte[]) {
            return send((byte[]) packet);
        }
        return send(packet.toString());
    }
}
