package com.bmrt.projectsea.websocket.websocket;

import com.bmrt.projectsea.domain.Action;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.ShipBuilder;
import io.quarkus.websockets.next.OpenConnections;
import io.quarkus.websockets.next.WebSocketConnection;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.List.of;
import static org.mockito.Mockito.*;

class ServerBroadcastTest {

    @Nested
    class sendToAllPLayer {

        @Test
        void caseTURN() {
            WebSocketConnection connection = mock(WebSocketConnection.class);
            OpenConnections connections = mock(OpenConnections.class);
            when(connections.listAll()).thenReturn(of(connection));
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.LEFT)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            ServerBroadcast serverBroadcast = new ServerBroadcast(connections);
            serverBroadcast.sendToAllPLayer(Action.TURN, ship);
            verify(connection, times(1)).sendText("TURN;10.0;10.0;0.0;0.0;LEFT;Test;5.0;5.0");
        }

        @Test
        void caseSTOP() {
            WebSocketConnection connection = mock(WebSocketConnection.class);
            OpenConnections connections = mock(OpenConnections.class);
            when(connections.listAll()).thenReturn(of(connection));
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.TOP)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            ServerBroadcast serverBroadcast = new ServerBroadcast(connections);
            serverBroadcast.sendToAllPLayer(Action.STOP, ship);
            verify(connection, times(1)).sendText("STOP;10.0;10.0;0.0;0.0;TOP;Test;5.0;5.0");
        }

        @Test
        void caseLEAVE() {
            WebSocketConnection connection = mock(WebSocketConnection.class);
            OpenConnections connections = mock(OpenConnections.class);
            when(connections.listAll()).thenReturn(of(connection));
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.TOP)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            ServerBroadcast serverBroadcast = new ServerBroadcast(connections);
            serverBroadcast.sendToAllPLayer(Action.LEAVE, ship);
            verify(connection, times(1)).sendText("LEAVE;10.0;10.0;0.0;0.0;TOP;Test;5.0;5.0");
        }

        @Test
        void caseJOIN() {
            WebSocketConnection connection = mock(WebSocketConnection.class);
            OpenConnections connections = mock(OpenConnections.class);
            when(connections.listAll()).thenReturn(of(connection));
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.TOP)
                .withName("Test")
                .withHealthPoint(5)
                .withMaxHealthPoint(5)
                .build();
            ServerBroadcast serverBroadcast = new ServerBroadcast(connections);
            serverBroadcast.sendToAllPLayer(Action.JOIN, ship);
            verify(connection, times(1)).sendText("JOIN;10.0;10.0;0" +
                ".0;0.0;TOP;Test;5.0;5.0");
        }

        @Test
        void caseSHOOT() {
            WebSocketConnection connection = mock(WebSocketConnection.class);
            OpenConnections connections = mock(OpenConnections.class);
            when(connections.listAll()).thenReturn(of(connection));
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .withDirection(Direction.LEFT)
                .withName("Target")
                .withHealthPoint(4)
                .withMaxHealthPoint(5)
                .build();
            ServerBroadcast serverBroadcast = new ServerBroadcast(connections);
            serverBroadcast.sendToAllPLayer(Action.SHOOT, ship);
            verify(connection, times(1)).sendText("SHOOT;10.0;10.0;0" +
                ".0;0.0;LEFT;Target;4.0;5.0");
        }

    }


}
