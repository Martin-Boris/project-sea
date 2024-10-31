package com.bmrt.projectsea.domain;

import java.util.Objects;

public class Ship {

    /* CONSTANT */
    public static final float MAX_HP = 10000;
    public static final float DAMAGE = 2500;
    private static final float SPEED_TILE_PER_SEC = 4;
    private static final float RANGE = 8;


    private final String name;
    private final float maxHealthPoint;
    private float healthPoint;
    private Vector position;
    private Vector speed;
    private Direction direction;

    public Ship(Vector position, Vector speed, Direction direction, String name, float healthPoint,
                float maxHealthPoint) {
        this.position = position;
        this.speed = speed;
        this.direction = direction;
        this.name = name;
        this.healthPoint = healthPoint;
        this.maxHealthPoint = maxHealthPoint;
    }

    public Ship updateDirection(float dt, Direction direction) {
        this.direction = direction;
        this.speed = getAcceleration(direction).mult(dt);
        return this;
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

    public Ship stop() {
        speed = Vector.ZERO;
        return this;
    }

    public String getName() {
        return name;
    }

    public float getHealthPoint() {
        return healthPoint;
    }

    public void shoot(Ship target) {
        target.applyDamage(DAMAGE);
    }

    private void applyDamage(float damageAmount) {
        if (healthPoint - damageAmount < 0) {
            healthPoint = 0;
        } else {
            healthPoint -= damageAmount;
        }
    }

    public boolean canShoot(Ship target) {
        return position.inRange(target.getPosition(), RANGE);
    }

    public boolean isSunk() {
        return healthPoint <= 0;
    }

    public float getPercentHp() {
        return healthPoint / maxHealthPoint;
    }

    public float getMaxHealthPoint() {
        return maxHealthPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return Float.compare(maxHealthPoint, ship.maxHealthPoint) == 0 && Float.compare(healthPoint,
            ship.healthPoint) == 0 && Objects.equals(name, ship.name) && Objects.equals(position, ship.position) && Objects.equals(speed, ship.speed) && direction == ship.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxHealthPoint, healthPoint, position, speed, direction);
    }

    @Override
    public String toString() {
        return "Ship{" +
            "name='" + name + '\'' +
            ", maxHealthPoint=" + maxHealthPoint +
            ", healthPoint=" + healthPoint +
            ", position=" + position +
            ", speed=" + speed +
            ", direction=" + direction +
            '}';
    }
}
