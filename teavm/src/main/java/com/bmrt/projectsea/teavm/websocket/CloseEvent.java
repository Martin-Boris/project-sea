package com.bmrt.projectsea.teavm.websocket;

import org.teavm.jso.JSProperty;
import org.teavm.jso.dom.events.Event;

/**
 * TeaVM JSO binding for WebSocket CloseEvent.
 */
public interface CloseEvent extends Event {

    @JSProperty
    int getCode();

    @JSProperty
    String getReason();

    @JSProperty
    boolean isWasClean();
}
