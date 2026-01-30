package com.bmrt.projectsea.teavm;

import com.bmrt.projectsea.teavm.websocket.TeaVMWebSocket;
import com.github.czyzby.websocket.CommonWebSockets;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketFactory;

/**
 * TeaVM WebSocket initialization.
 * Call initiate() before using WebSockets in the application.
 */
public class TeaVMWebSockets {

    private static boolean initiated = false;

    /**
     * Initializes TeaVM WebSocket support.
     * Must be called before creating any WebSocket connections.
     */
    public static void initiate() {
        if (initiated) {
            return;
        }
        initiated = true;
        CommonWebSockets.initiate(new WebSocketFactory() {
            @Override
            public WebSocket newWebSocket(String url) {
                return new TeaVMWebSocket(url);
            }
        });
    }
}
