package com.bmrt.projectsea.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipTest {

    @Nested
    class updateDirection {

        @Test
        public void caseLeft() {
            Ship ship = ShipBuilder
                .newShip()
                .withDirection(Direction.BOT)
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .build();
            ship.updateDirection(0.1f, Direction.LEFT);
            assertEquals(ship.getDirection(), Direction.LEFT);
            assertEquals(ship.getPosition(), new Vector(10f, 10));
            assertEquals(ship.getSpeed(), new Vector(-0.4f, 0));
        }

        @Test
        public void caseRight() {
            Ship ship = ShipBuilder
                .newShip()
                .withDirection(Direction.BOT)
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .build();
            ship.updateDirection(0.1f, Direction.RIGHT);
            assertEquals(ship.getDirection(), Direction.RIGHT);
            assertEquals(ship.getPosition(), new Vector(10f, 10));
            assertEquals(ship.getSpeed(), new Vector(0.4f, 0));
        }

        @Test
        public void caseTop() {
            Ship ship = ShipBuilder
                .newShip()
                .withDirection(Direction.BOT)
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .build();
            ship.updateDirection(0.1f, Direction.TOP);
            assertEquals(ship.getDirection(), Direction.TOP);
            assertEquals(ship.getPosition(), new Vector(10f, 10f));
            assertEquals(ship.getSpeed(), new Vector(0f, 0.4f));
        }

        @Test
        public void caseBot() {
            Ship ship = ShipBuilder
                .newShip()
                .withDirection(Direction.TOP)
                .withPosition(10, 10)
                .withSpeed(0, 0)
                .build();
            ship.updateDirection(0.1f, Direction.BOT);
            assertEquals(ship.getDirection(), Direction.BOT);
            assertEquals(ship.getPosition(), new Vector(10f, 10f));
            assertEquals(ship.getSpeed(), new Vector(0f, -0.4f));
        }
    }

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
}
