package com.bmrt.projectsea.application;

import com.bmrt.projectsea.domain.*;
import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import com.bmrt.projectsea.infrastructure.GameLoop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameInstanceServiceTest {

    private GameInstanceService service;
    private GameInstance gameInstance;
    private GameLoop gameLoop;
    private ClientCommunicationPort clientCommunicationPort;

    @BeforeEach
    void setUp() {
        gameInstance = new GameInstance(new SeaMap(20, 20), java.util.Collections.emptyList());
        gameLoop = mock(GameLoop.class);
        clientCommunicationPort = mock(ClientCommunicationPort.class);
        service = new GameInstanceService(gameInstance, gameLoop, clientCommunicationPort);
    }

    @Test
    void join_startsGameLoopIfNotRunning() {
        when(gameLoop.isRunning()).thenReturn(false);

        service.join("Player1", 0, 0);

        verify(gameLoop, times(1)).start();
    }

    @Test
    void join_doesNotStartGameLoopIfAlreadyRunning() {
        when(gameLoop.isRunning()).thenReturn(true);

        service.join("Player1", 0, 0);

        verify(gameLoop, Mockito.never()).start();
    }

    @Test
    void join_broadcastsJoinAction() {
        when(gameLoop.isRunning()).thenReturn(true);
        Ship expectedShip = ShipBuilder.newShip()
            .withName("Player1")
            .withPosition(5, 10)
            .withSpeed(0, 0)
            .withDirection(Direction.BOT)
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .build();

        Ship ship = service.join("Player1", 5, 10);

        assertEquals(expectedShip, ship);
        verify(clientCommunicationPort, times(1)).sendToAllPLayer(Action.JOIN, ship);
    }

    @Test
    void leave_broadcastsLeaveAction() {
        when(gameLoop.isRunning()).thenReturn(true);
        gameInstance.playerJoin("Player1", 0, 0);

        Ship ship = service.leave("Player1");

        verify(clientCommunicationPort, times(1)).sendToAllPLayer(Action.LEAVE, ship);
    }

    @Test
    void leave_stopsGameLoopWhenLastPlayerLeaves() {
        when(gameLoop.isRunning()).thenReturn(true);
        gameInstance.playerJoin("Player1", 0, 0);

        service.leave("Player1");

        verify(gameLoop, times(1)).stop();
    }

    @Test
    void leave_doesNotStopGameLoopWhenOtherPlayersRemain() {
        when(gameLoop.isRunning()).thenReturn(true);
        gameInstance.playerJoin("Player1", 0, 0);
        gameInstance.playerJoin("Player2", 5, 5);

        service.leave("Player1");

        verify(gameLoop, Mockito.never()).stop();
    }

    @Test
    void updateDirection_broadcastsTurnAction() {
        when(gameLoop.isRunning()).thenReturn(true);
        gameInstance.playerJoin("Player1", 0, 0);

        Ship ship = service.updateDirection(Direction.LEFT, "Player1");

        verify(clientCommunicationPort, times(1)).sendToAllPLayer(Action.TURN, ship);
    }

    @Test
    void stop_broadcastsStopAction() {
        when(gameLoop.isRunning()).thenReturn(true);
        gameInstance.playerJoin("Player1", 0, 0);

        Ship ship = service.stop("Player1");

        verify(clientCommunicationPort, times(1)).sendToAllPLayer(Action.STOP, ship);
    }

    @Test
    void getShips_returnsAllShips() {
        gameInstance.playerJoin("Player1", 0, 0);
        gameInstance.playerJoin("Player2", 5, 5);

        assertEquals(2, service.getShips().size());
    }

    @Nested
    class shoot {

        @Test
        void shoot_broadcastsShootAction() throws InvalidTarget, TargetToFar {
            when(gameLoop.isRunning()).thenReturn(true);
            gameInstance.playerJoin("Shooter", 0, 0);
            gameInstance.playerJoin("Target", 2, 2);

            Ship target = service.shoot("Shooter", "Target");

            verify(clientCommunicationPort, times(1)).sendToAllPLayer(Action.SHOOT, target);
        }

        @Test
        void shoot_throwsInvalidTargetWhenTargetDoesNotExist() {
            when(gameLoop.isRunning()).thenReturn(true);
            gameInstance.playerJoin("Shooter", 0, 0);

            assertThrows(InvalidTarget.class, () ->
                service.shoot("Shooter", "NonExistent")
            );
        }

        @Test
        void shoot_throwsTargetToFarWhenTargetOutOfRange() {
            when(gameLoop.isRunning()).thenReturn(true);
            gameInstance.playerJoin("Shooter", 0, 0);
            gameInstance.playerJoin("Target", 50, 50);

            assertThrows(TargetToFar.class, () ->
                service.shoot("Shooter", "Target")
            );
        }
    }
}
