package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipTest {

    @Nested
    class update {

        @Test
        void casePositionUpdate() {
            Ship ship = ShipBuilder.newShip()
                .withSpeed(1, 0)
                .withPosition(10, 10)
                .build();
            ship.update(new SeaMap(20, 20));
            assertEquals(ship.getPosition(), new Vector(11, 10));
        }

        @Test
        void caseOutSeaMapOnLeft() {
            Ship ship = ShipBuilder.newShip()
                .withSpeed(-1, 0)
                .withPosition(0, 10)
                .build();
            ship.update(new SeaMap(20, 20));
            assertEquals(ship.getPosition(), new Vector(0, 10));
        }

        @Test
        void caseOutSeaMapOnBottom() {
            Ship ship = ShipBuilder.newShip()
                .withSpeed(0, -1)
                .withPosition(0, 0)
                .build();
            ship.update(new SeaMap(20, 20));
            assertEquals(ship.getPosition(), new Vector(0, 0));
        }

        @Test
        void caseOutSeaMapOnRight() {
            Ship ship = ShipBuilder.newShip()
                .withSpeed(1, 0)
                .withPosition(10, 10)
                .build();
            ship.update(new SeaMap(10, 10));
            assertEquals(ship.getPosition(), new Vector(10, 10));
        }

        @Test
        void caseOutSeaMapOnTop() {
            Ship ship = ShipBuilder.newShip()
                .withSpeed(0, 1)
                .withPosition(10, 10)
                .build();
            ship.update(new SeaMap(10, 10));
            assertEquals(ship.getPosition(), new Vector(10, 10));
        }
    }

    @Nested
    class shoot {
        @Test
        void caseShootTarget() {
            Ship myShip = ShipBuilder
                .newShip()
                .build();

            Ship target = ShipBuilder
                .newShip()
                .withHealthPoint(10000)
                .build();
            myShip.shoot(target);
            Assertions.assertEquals(target.getHealthPoint(), 7500);
        }

        @Test
        void caseShootTargetWithoutHP() {
            Ship myShip = ShipBuilder
                .newShip()
                .build();

            Ship target = ShipBuilder
                .newShip()
                .withHealthPoint(0)
                .build();
            myShip.shoot(target);
            Assertions.assertEquals(target.getHealthPoint(), 0);
        }

    }

    @Nested
    class canShoot {
        @Test
        void caseTrue() {
            Ship ship = ShipBuilder.newShip()
                .withPosition(10, 10)
                .build();
            Ship target = ShipBuilder.newShip()
                .withPosition(11, 10)
                .build();
            Assertions.assertTrue(ship.canShoot(target));
        }

        @Test
        void caseFalse() {
            Ship ship = ShipBuilder.newShip()
                .withPosition(10, 10)
                .build();
            Ship target = ShipBuilder.newShip()
                .withPosition(20, 10)
                .build();
            Assertions.assertFalse(ship.canShoot(target));
        }
    }

}
