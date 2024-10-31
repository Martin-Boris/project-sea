package com.bmrt.projectsea.domain;

import java.util.Objects;

public class Ship {

    /* CONSTANT */
    public static final float MAX_HP = 10000;
    public static final float DAMAGE = 2500;
    private static final float SPEED_TILE_PER_SEC = 4;
    private static final float RANGE = 8;
    private final Vector speed;
    private String name;
    private float maxHealthPoint;
    private float healthPoint;
    private Vector position;
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

    public Vector getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setSpeed(float x, float y) {
        this.speed.x = x;
        this.speed.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void update(SeaMap seaMap) {
        Vector newPosition = position.add(speed);
        if (!seaMap.isOut(newPosition)) {
            position = newPosition;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(float healthPoint) {
        this.healthPoint = healthPoint;
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

    public void setMaxHealthPoint(float maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
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
