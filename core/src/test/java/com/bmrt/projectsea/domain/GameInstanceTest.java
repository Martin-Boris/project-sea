package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.websocket.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
            Mockito.verify(renderPort, Mockito.times(1)).remove("Test");
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
            Mockito.verify(renderPort, Mockito.never()).triggerPortShoot(Mockito.anyString());
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
            Mockito.verify(renderPort, Mockito.never()).triggerPortShoot(Mockito.anyString());
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
            Mockito.verify(renderPort, Mockito.times(1)).triggerPortShoot(myShipName);
            Mockito.verify(webSocketPort, Mockito.times(1)).shoot(myShipName, "Target");
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
            Mockito.verify(renderPort, Mockito.never()).triggerPortShoot(Mockito.anyString());
            Mockito.verify(portCooldown, Mockito.times(0)).trigger();
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
            Mockito.verify(renderPort, Mockito.never()).triggerStarboardShoot(Mockito.anyString());
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
            Mockito.verify(renderPort, Mockito.never()).triggerStarboardShoot(Mockito.anyString());
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
            Mockito.verify(renderPort, Mockito.times(1)).triggerStarboardShoot(myShipName);
            Mockito.verify(webSocketPort, Mockito.times(1)).shoot(myShipName, "Target");
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
            Mockito.verify(renderPort, Mockito.never()).triggerStarboardShoot(Mockito.anyString());
            Mockito.verify(starboardCooldown, Mockito.times(0)).trigger();
        }
    }
}
