package com.bmrt.projectsea.domain;

public class Ship {

    private static final float SPEED_TILE_PER_SEC = 4;
    private final String name;
    private Vector position;
    private Vector speed;
    private Direction direction;

    public Ship(Vector position, Vector speed, Direction direction, String name) {
        this.position = position;
        this.speed = speed;
        this.direction = direction;
        this.name = name;
    }

    public void updateDirection(float dt, Direction direction) {
        this.direction = direction;
        speed = getAcceleration(direction).mult(dt);
        position = position.add(speed);
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void update(SeaMap seaMap) {
        Vector newPosition = position.add(speed);
        if (!seaMap.isOut(newPosition)) {
            position = newPosition;
        }
    }

    private Vector getAcceleration(Direction direction) {
        switch (direction) {
            case TOP:
                return new Vector(0, SPEED_TILE_PER_SEC);
            case LEFT:
                return new Vector(-SPEED_TILE_PER_SEC, 0);
            case RIGHT:
                return new Vector(SPEED_TILE_PER_SEC, 0);
            default:
                return new Vector(0, -SPEED_TILE_PER_SEC);
        }
    }

    public void stop() {
        speed = Vector.ZERO;
    }

    public String getName() {
        return name;
    }
}
