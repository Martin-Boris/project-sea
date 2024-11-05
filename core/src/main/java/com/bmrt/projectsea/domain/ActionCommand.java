package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.websocket.Action;

import java.util.Objects;

public class ActionCommand {

    private final Action action;
    private final float x;
    private final float y;
    private final float speedX;
    private final float speedY;
    private final float healthPoint;
    private final float maxHealthPoint;
    private final String name;
    private final Direction direction;

    public ActionCommand(Action action, float x, float y, float speedX, float speedY, float healthPoint,
                         float maxHealthPoint, String name, Direction direction) {
        this.action = action;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.healthPoint = healthPoint;
        this.maxHealthPoint = maxHealthPoint;
        this.name = name;
        this.direction = direction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionCommand that = (ActionCommand) o;
        return Float.compare(x, that.x) == 0 && Float.compare(y, that.y) == 0 && Float.compare(speedX, that.speedX) == 0 && Float.compare(speedY, that.speedY) == 0 && Float.compare(healthPoint, that.healthPoint) == 0 && Float.compare(maxHealthPoint, that.maxHealthPoint) == 0 && action == that.action && Objects.equals(name, that.name) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, x, y, speedX, speedY, healthPoint, maxHealthPoint, name, direction);
    }

    public Action getAction() {
        return action;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public float getHealthPoint() {
        return healthPoint;
    }

    public float getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public Ship getNewShip() {
        return new Ship(new Vector(x, y), new Vector(speedX, speedY), direction, name, healthPoint, maxHealthPoint);
    }

    public void updateShip(Ship ship) {
        ship.setDirection(direction);
        ship.setName(name);
        ship.setSpeed(speedX, speedY);
        ship.setPosition(x, y);
        ship.setHealthPoint(healthPoint);
        ship.setMaxHealthPoint(maxHealthPoint);
    }
}
