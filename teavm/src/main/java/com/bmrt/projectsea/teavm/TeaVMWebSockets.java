package com.bmrt.projectsea.teavm;

import com.bmrt.projectsea.teavm.websocket.TeaVMWebSocket;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;

/**
 * TeaVM WebSocket initialization.
 * Extends WebSockets to set the protected FACTORY field.
 * Call initiate() before using WebSockets in the application.
 */
public class TeaVMWebSockets extends WebSockets {

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
        // Set the protected FACTORY field from parent WebSockets class
        WebSockets.FACTORY = new WebSocketFactory() {
            @Override
            public WebSocket newWebSocket(String url) {
                return new TeaVMWebSocket(url);
            }
        };
    }
}
