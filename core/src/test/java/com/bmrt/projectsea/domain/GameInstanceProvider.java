package com.bmrt.projectsea.domain;

import org.mockito.Mockito;

public class GameInstanceProvider {

    private final RenderPort renderPort = Mockito.mock(RenderPort.class);
    WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);

    public GameInstance get(String shipName) {
        return new GameInstance(shipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
    }

    public RenderPort getRenderPort() {
        return renderPort;
    }

    public WebSocketPort getWebSocketPort() {
        return webSocketPort;
    }
}
