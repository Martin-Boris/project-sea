package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameInstanceTest {


    @Test
    void caseInitGameInstance() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Torred")
            .withDirection(Direction.TOP)
            .withPosition(10, 5)
            .withSpeed(0, 0)
            .withMaxHealthPoint(Ship.MAX_HP)
            .withHealthPoint(Ship.MAX_HP)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", null, null);
        Assertions.assertEquals(gameInstance.getMyShip(), ship);
    }

    @Test
    void caseAddShip() {
        Ship ship = ShipBuilder
            .newShip()
            .withName("Test")
            .withDirection(Direction.TOP)
            .withPosition(10, 10)
            .withSpeed(0, 0)
            .build();
        GameInstance gameInstance = new GameInstance("Torred", null, null);
        gameInstance.add(ship);
        Assertions.assertEquals(gameInstance.get("Test"), ship);
    }

}
