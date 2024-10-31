package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
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
        Ship ship = gameInstance.join("Name");
        Assertions.assertEquals(ship, expectedShip);
        Assertions.assertTrue(gameInstance.contains(expectedShip));
    }

    @Test
    void caseLeave() {
        GameInstance gameInstance = new GameInstance();
        Ship ship = gameInstance.join("Name");
        Ship leaveShip = gameInstance.leave("Name");
        Assertions.assertEquals(ship, leaveShip);
        Assertions.assertFalse(gameInstance.contains(ship));
    }

    @Test
    void caseStop() {
        GameInstance gameInstance = new GameInstance();
        Ship ship = gameInstance.join("Name");
        Ship stopShip = gameInstance.stop("Name");
        Assertions.assertEquals(ship, stopShip);
        Assertions.assertEquals(stopShip.getSpeed(), new Vector(0, 0));
        Assertions.assertTrue(gameInstance.contains(ship));
    }

    @Test
    void caseUpdateDirection() {
        GameInstance gameInstance = new GameInstance();
        Ship ship = gameInstance.join("Name");
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
}

