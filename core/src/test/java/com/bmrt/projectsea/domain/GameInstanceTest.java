package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.command.ActionCommand;
import com.bmrt.projectsea.domain.command.ActionCommandBuilder;
import com.bmrt.projectsea.domain.command.ShootCommand;
import com.bmrt.projectsea.domain.command.ShootCommandBuilder;
import com.bmrt.projectsea.websocket.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GameInstanceTest {


    @Test
    void caseAddShip() {
        WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
        RenderPort renderPort = Mockito.mock(RenderPort.class);
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.TOP)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort, new Cooldown(), new Cooldown());
        gameInstance.addShip(ship);
        Assertions.assertEquals(gameInstance.get("Test"), ship);
    }

    @Nested
    class handleAction {

        @Test
        void caseJOINAction() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            ActionCommand cmd = ActionCommandBuilder.anActionCommand()
                .withAction(Action.JOIN)
                .withX(10.0f)
                .withY(11.0f)
                .withSpeedX(0.0f)
                .withSpeedY(1.0f)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 11)
                .withSpeed(0, 1)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort, new Cooldown(), new Cooldown());
            gameInstance.handleAction(cmd);
            Assertions.assertTrue(gameInstance.contains("Test"));
            Assertions.assertEquals(gameInstance.get("Test"), ship);
        }

        @Test
        void caseLEAVEAction() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            ActionCommand cmd = ActionCommandBuilder.anActionCommand()
                .withAction(Action.LEAVE)
                .withX(10.0f)
                .withY(11.0f)
                .withSpeedX(0.0f)
                .withSpeedY(1.0f)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 11)
                .withSpeed(0, 1)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort, new Cooldown(), new Cooldown());
            gameInstance.addShip(ship);
            gameInstance.handleAction(cmd);
            Assertions.assertFalse(gameInstance.contains("Test"));
            verify(renderPort, times(1)).remove("Test");
        }

        @Test
        void caseSTOPAction() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            ActionCommand cmd = ActionCommandBuilder.anActionCommand()
                .withAction(Action.STOP)
                .withX(10.0f)
                .withY(11.0f)
                .withSpeedX(0.0f)
                .withSpeedY(0.0f)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 11)
                .withSpeed(0, 1)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort, new Cooldown(), new Cooldown());
            gameInstance.addShip(ship);
            gameInstance.handleAction(cmd);

            Ship expectedShip = ShipBuilder
                .newShip()
                .withPosition(10, 11)
                .withSpeed(0, 0)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            Assertions.assertTrue(gameInstance.contains("Test"));
            Assertions.assertEquals(gameInstance.get("Test"), expectedShip);
        }

        @Test
        void caseTURNAction() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            ActionCommand cmd = ActionCommandBuilder.anActionCommand()
                .withAction(Action.TURN)
                .withX(10.0f)
                .withY(11.0f)
                .withSpeedX(0.0f)
                .withSpeedY(-1.0f)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            Ship ship = ShipBuilder
                .newShip()
                .withPosition(10, 11)
                .withSpeed(0, 1)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.TOP)
                .build();
            GameInstance gameInstance = new GameInstance("Torred", renderPort, webSocketPort, new Cooldown(), new Cooldown());
            gameInstance.addShip(ship);
            gameInstance.handleAction(cmd);

            Ship expectedShip = ShipBuilder
                .newShip()
                .withPosition(10, 11)
                .withSpeed(0, -1)
                .withHealthPoint(9000.0f)
                .withMaxHealthPoint(10000.0f)
                .withName("Test")
                .withDirection(Direction.BOT)
                .build();
            Assertions.assertTrue(gameInstance.contains("Test"));
            Assertions.assertEquals(gameInstance.get("Test"), expectedShip);
        }

    }

    @Nested
    class triggerPortShoot {

        @Test
        void caseNoTarget() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
            Ship ship = ShipBuilder
                .newShip()
                .withName(myShipName)
                .build();
            gameInstance.addShip(ship);
            gameInstance.triggerPortShoot();
            verify(webSocketPort, never()).shoot(anyString(), anyString());
        }

        @Test
        void caseTargetToFar() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
            Ship ship = ShipBuilder
                .newShip().withPosition(0, 0)
                .withName(myShipName)
                .build();
            Ship target = ShipBuilder
                .newShip().withPosition(50, 50)
                .withName("Target")
                .build();
            gameInstance.addShip(ship);
            gameInstance.addShip(target);
            gameInstance.setTarget(target);
            gameInstance.triggerPortShoot();
            verify(webSocketPort, never()).shoot(anyString(), anyString());
        }

        @Test
        void caseTargetCloseAndShootReady() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
            Ship ship = ShipBuilder
                .newShip().withPosition(0, 0)
                .withName(myShipName)
                .build();
            Ship target = ShipBuilder
                .newShip().withPosition(5f, 3f)
                .withName("Target")
                .build();
            gameInstance.addShip(ship);
            gameInstance.addShip(target);
            gameInstance.setTarget(target);
            gameInstance.triggerPortShoot();
            verify(webSocketPort, times(1)).shoot(myShipName, "Target");
        }

        @Test
        void caseTargetCloseAndShootNotReady() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            Cooldown portCooldown = Mockito.mock(Cooldown.class);
            Mockito.when(portCooldown.isReady()).thenReturn(false);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, portCooldown, new Cooldown());
            Ship ship = ShipBuilder
                .newShip().withPosition(0, 0)
                .withName(myShipName)
                .build();
            Ship target = ShipBuilder
                .newShip().withPosition(5f, 3f)
                .withName("Target")
                .build();
            gameInstance.addShip(ship);
            gameInstance.addShip(target);
            gameInstance.setTarget(target);
            gameInstance.triggerPortShoot();
            verify(portCooldown, times(0)).trigger();
            verify(webSocketPort, never()).shoot(anyString(), anyString());
        }
    }

    @Nested
    class triggerStarboardShoot {

        @Test
        void caseNoTarget() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
            Ship ship = ShipBuilder
                .newShip()
                .withName(myShipName)
                .build();
            gameInstance.addShip(ship);
            gameInstance.triggerStarboardShoot();
            verify(webSocketPort, never()).shoot(anyString(), anyString());
        }

        @Test
        void caseTargetToFar() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
            Ship ship = ShipBuilder
                .newShip().withPosition(0, 0)
                .withName(myShipName)
                .build();
            Ship target = ShipBuilder
                .newShip().withPosition(50, 50)
                .withName("Target")
                .build();
            gameInstance.addShip(ship);
            gameInstance.addShip(target);
            gameInstance.setTarget(target);
            gameInstance.triggerStarboardShoot();
            verify(webSocketPort, never()).shoot(anyString(), anyString());
        }

        @Test
        void caseTargetCloseAndShootReady() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), new Cooldown());
            Ship ship = ShipBuilder
                .newShip().withPosition(0, 0)
                .withName(myShipName)
                .build();
            Ship target = ShipBuilder
                .newShip().withPosition(5f, 3f)
                .withName("Target")
                .build();
            gameInstance.addShip(ship);
            gameInstance.addShip(target);
            gameInstance.setTarget(target);
            gameInstance.triggerStarboardShoot();
            verify(webSocketPort, times(1)).shoot(myShipName, "Target");
        }

        @Test
        void caseTargetCloseAndShootNotReady() {
            WebSocketPort webSocketPort = Mockito.mock(WebSocketPort.class);
            RenderPort renderPort = Mockito.mock(RenderPort.class);
            String myShipName = "Test";
            Cooldown starboardCooldown = Mockito.mock(Cooldown.class);
            Mockito.when(starboardCooldown.isReady()).thenReturn(false);
            GameInstance gameInstance = new GameInstance(myShipName, renderPort, webSocketPort, new Cooldown(), starboardCooldown);
            Ship ship = ShipBuilder
                .newShip().withPosition(0, 0)
                .withName(myShipName)
                .build();
            Ship target = ShipBuilder
                .newShip().withPosition(5f, 3f)
                .withName("Target")
                .build();
            gameInstance.addShip(ship);
            gameInstance.addShip(target);
            gameInstance.setTarget(target);
            gameInstance.triggerStarboardShoot();
            verify(webSocketPort, never()).shoot(anyString(), anyString());
            verify(starboardCooldown, times(0)).trigger();
        }
    }

    @Nested
    class RenderShoot {
        @Test
        void shouldRenderShoot() {
            GameInstanceProvider gameInstanceProvider = new GameInstanceProvider();
            ShootCommand cmd = ShootCommandBuilder.aShootCommand()
                .withHealthPoint(7500.0f)
                .withMaxHealthPoint(10000.0f)
                .withTargetName("target")
                .withShooterName("shooter")
                .build();

            gameInstanceProvider.get("shooter").renderShoot(cmd);

            verify(gameInstanceProvider.getRenderPort(), times(1)).renderPortShoot(cmd);

        }
    }
}
