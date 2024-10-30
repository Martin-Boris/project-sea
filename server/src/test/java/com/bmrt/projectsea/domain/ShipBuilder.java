package com.bmrt.projectsea.domain;

public class ShipBuilder {

    private Vector position;
    private Vector speed;
    private String name;
    private Direction direction;
    private int healthPoint;
    private int maxHealthPoint;

    public static ShipBuilder newShip() {
        return new ShipBuilder();
    }

    public ShipBuilder withName(String name) {
        this.name = name;
        return this;
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

    public ShipBuilder withHealthPoint(int hp) {
        this.healthPoint = hp;
        return this;
    }

    public ShipBuilder withMaxHealthPoint(int hp) {
        this.maxHealthPoint = hp;
        return this;
    }

    public Ship build() {
        return new Ship(position, speed, direction, name, healthPoint, maxHealthPoint);
    }


}
