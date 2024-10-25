package com.bmrt.projectsea.domain;

public class ShipBuilder {

    private Vector position;
    private Vector speed;
    private Direction direction;

    public static ShipBuilder newShip() {
        return new ShipBuilder();
    }

    public ShipBuilder withPosition(float x, float y) {
        position = new Vector(x, y);
        return this;
    }

    public ShipBuilder withSpeed(float x, float y) {
        speed = new Vector(x, y);
        return this;
    }

    public ShipBuilder withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public Ship build() {
        return new Ship(position, speed, direction, "");
    }


}
