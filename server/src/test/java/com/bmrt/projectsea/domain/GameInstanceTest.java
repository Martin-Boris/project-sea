package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.domain.errors.InvalidTarget;
import com.bmrt.projectsea.domain.errors.TargetToFar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameInstanceTest {

    private GameInstance gameInstance;

    @BeforeEach
    void setUp() {
        gameInstance = new GameInstance(new SeaMap(20, 20));
    }

    @Test
    void caseJoin() {
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

        Assertions.assertEquals(expectedShip, ship);
        Assertions.assertTrue(gameInstance.contains(expectedShip));
    }

    @Test
    void caseLeave() {
        Ship ship = gameInstance.join("Name", 0, 0);

        Ship leaveShip = gameInstance.leave("Name");

        Assertions.assertEquals(ship, leaveShip);
        Assertions.assertFalse(gameInstance.contains(ship));
    }

    @Test
    void caseStop() {
        Ship ship = gameInstance.join("Name", 0, 0);

        Ship stopShip = gameInstance.stop("Name");

        Assertions.assertEquals(ship, stopShip);
        Assertions.assertEquals(new Vector(0, 0), stopShip.getSpeed());
        Assertions.assertTrue(gameInstance.contains(ship));
    }

    @Test
    void caseUpdateDirection() {
        gameInstance.join("Name", 0, 0);
        Ship expectedShip = ShipBuilder
            .newShip()
            .withDirection(Direction.LEFT)
            .withName("Name")
            .withHealthPoint(10000)
            .withMaxHealthPoint(10000)
            .withPosition(0, 0)
            .withSpeed(-0.06666667f, 0.0f)
            .build();

        Ship ship = gameInstance.updateDirection(Direction.LEFT, "Name");

        Assertions.assertEquals(expectedShip, ship);
    }

    @Test
    void caseIsEmpty() {
        Assertions.assertTrue(gameInstance.isEmpty());

        gameInstance.join("Name", 0, 0);
        Assertions.assertFalse(gameInstance.isEmpty());

        gameInstance.leave("Name");
        Assertions.assertTrue(gameInstance.isEmpty());
    }

    @Nested
    class shoot {

        @Test
        void caseNoValidTarget() {
            gameInstance.join("Name", 0, 0);

            Assertions.assertThrows(InvalidTarget.class, () ->
                gameInstance.shoot("Name", "")
            );
        }

        @Test
        void caseTargetToFar() {
            gameInstance.join("Name", 0, 0);
            gameInstance.join("Target", 50, 50);

            Assertions.assertThrows(TargetToFar.class, () ->
                gameInstance.shoot("Name", "Target")
            );
        }

        @Test
        void caseShootTriggered() throws TargetToFar, InvalidTarget {
            gameInstance.join("Name", 0, 0);
            gameInstance.join("Target", 2, 2);
            Ship expectedShip = ShipBuilder
                .newShip()
                .withDirection(Direction.BOT)
                .withName("Target")
                .withHealthPoint(7500)
                .withMaxHealthPoint(10000)
                .withPosition(2, 2)
                .withSpeed(0, 0)
                .build();

            Ship target = gameInstance.shoot("Name", "Target");

            Assertions.assertEquals(expectedShip, target);
        }
    }
}
