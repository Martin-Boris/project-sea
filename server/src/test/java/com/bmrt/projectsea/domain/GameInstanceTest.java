package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameInstanceTest {

    @Test
    void caseJoin() {
        GameInstance gameInstance = new GameInstance();
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.BOT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(0, 0)
            .build();
        Ship ship = gameInstance.join("Name", 0, 0);
        Assertions.assertEquals(ship, expectedShip);
        Assertions.assertTrue(gameInstance.contains(expectedShip));
    }

    @Test
    void caseLeave() {
        GameInstance gameInstance = new GameInstance();
        Ship ship = gameInstance.join("Name", 0, 0);
        Ship leaveShip = gameInstance.leave("Name");
        Assertions.assertEquals(ship, leaveShip);
        Assertions.assertFalse(gameInstance.contains(ship));
    }

    @Test
    void caseStop() {
        GameInstance gameInstance = new GameInstance();
        Ship ship = gameInstance.join("Name", 0, 0);
        Ship stopShip = gameInstance.stop("Name");
        Assertions.assertEquals(ship, stopShip);
        Assertions.assertEquals(stopShip.getSpeed(), new Vector(0, 0));
        Assertions.assertTrue(gameInstance.contains(ship));
    }

    @Test
    void caseUpdateDirection() {
        GameInstance gameInstance = new GameInstance();
        Ship ship = gameInstance.join("Name", 0, 0);
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.LEFT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(-0.06666667f, 0.0f)
            .build();
        gameInstance.updateDirection(Direction.LEFT, "Name");
        Assertions.assertEquals(ship, expectedShip);
    }

    @Nested
    class shoot {

        @Test
        void caseNoValidTarget() {
            GameInstance gameInstance = new GameInstance();
            Ship ship = gameInstance.join("Name", 0, 0);
            try {
                gameInstance.shoot("Name", "");
                Assertions.fail();
            } catch (InvalidTarget | TargetToFar e) {
                Assertions.assertTrue(true);
            }
        }

        @Test
        void caseTargetToFar() {
            GameInstance gameInstance = new GameInstance();
            Ship ship = gameInstance.join("Name", 0, 0);
            Ship targetShip = gameInstance.join("Target", 50, 50);
            try {
                gameInstance.shoot("Name", "Target");
                Assertions.fail();
            } catch (TargetToFar | InvalidTarget e) {
                Assertions.assertTrue(true);
            }
        }

        @Test
        void caseShootTriggered() {
            GameInstance gameInstance = new GameInstance();
            Ship ship = gameInstance.join("Name", 0, 0);
            Ship targetShip = gameInstance.join("Target", 2, 2);
            try {
                Ship target = gameInstance.shoot("Name", "Target");
                Ship expectedShip = ShipBuilder
                    .newShip()
                    .withDirection(Direction.BOT)
                    .withName("Target")
                    .withHealthPoint(7500)
                    .withMaxHealthPoint(10000)
                    .withPosition(2, 2)
                    .withSpeed(0, 0)
                    .build();
                Assertions.assertEquals(target, expectedShip);
            } catch (TargetToFar | InvalidTarget e) {
                Assertions.fail();
            }
        }
    }
}

