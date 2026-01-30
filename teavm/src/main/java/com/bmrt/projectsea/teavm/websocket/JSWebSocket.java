package com.bmrt.projectsea.teavm.websocket;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MessageEvent;

/**
 * TeaVM JSO binding for the browser's WebSocket API.
 */
public abstract class JSWebSocket implements JSObject {

    @JSBody(params = {"url"}, script = "return new WebSocket(url);")
    public static native JSWebSocket create(String url);

    @JSProperty
    public abstract int getReadyState();

    @JSProperty
    public abstract String getUrl();

    @JSBody(params = {"data"}, script = "this.send(data);")
    public abstract void send(String data);

    @JSBody(params = {"data"}, script = "this.send(data);")
    public abstract void send(byte[] data);

    @JSBody(params = {}, script = "this.close();")
    public abstract void close();

    @JSBody(params = {"code", "reason"}, script = "this.close(code, reason);")
    public abstract void close(int code, String reason);

    @JSBody(params = {"listener"}, script = "this.onopen = function(e) { listener.handleEvent(e); };")
    public abstract void setOnOpen(EventListener<Event> listener);

    @JSBody(params = {"listener"}, script = "this.onclose = function(e) { listener.handleEvent(e); };")
    public abstract void setOnClose(EventListener<CloseEvent> listener);

    @JSBody(params = {"listener"}, script = "this.onmessage = function(e) { listener.handleEvent(e); };")
    public abstract void setOnMessage(EventListener<MessageEvent> listener);

    @JSBody(params = {"listener"}, script = "this.onerror = function(e) { listener.handleEvent(e); };")
    public abstract void setOnError(EventListener<Event> listener);

    // WebSocket ready states
    public static final int CONNECTING = 0;
    public static final int OPEN = 1;
    public static final int CLOSING = 2;
    public static final int CLOSED = 3;
}
