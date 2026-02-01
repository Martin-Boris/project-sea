package com.github.czyzby.websocket;


import com.github.czyzby.websocket.impl.TeaWebSocket;

/** Allows to initiate TeaVM web sockets module.
 * to remove when https://github.com/MrStahlfelge/gdx-websockets/pull/17 is merged
 * @author SimonIT */
public class TeaWebSockets {

    /** Initiates {@link WebSockets.WebSocketFactory}. */
    public static void initiate() {
        WebSockets.FACTORY = new TeaWebSocketFactory();
    }

    /** Provides {@link TeaWebSocket} instances.
     *
     * @author SimonIT */
    protected static class TeaWebSocketFactory implements WebSockets.WebSocketFactory {
        @Override
        public WebSocket newWebSocket(final String url) {
            return new TeaWebSocket(url);
        }
    }
}
