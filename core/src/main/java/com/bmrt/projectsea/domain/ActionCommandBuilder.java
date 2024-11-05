package com.bmrt.projectsea.domain;

import com.bmrt.projectsea.websocket.Action;

public final class ActionCommandBuilder {
    private Action action;
    private float x;
    private float y;
    private float speedX;
    private float speedY;
    private float healthPoint;
    private float maxHealthPoint;
    private String name;
    private Direction direction;

    private ActionCommandBuilder() {
    }

    public static ActionCommandBuilder anActionCommand() {
        return new ActionCommandBuilder();
    }

    public ActionCommandBuilder withAction(Action action) {
        this.action = action;
        return this;
    }

    public ActionCommandBuilder withX(float x) {
        this.x = x;
        return this;
    }

    public ActionCommandBuilder withY(float y) {
        this.y = y;
        return this;
    }

    public ActionCommandBuilder withSpeedX(float speedX) {
        this.speedX = speedX;
        return this;
    }

    public ActionCommandBuilder withSpeedY(float speedY) {
        this.speedY = speedY;
        return this;
    }

    public ActionCommandBuilder withHealthPoint(float healthPoint) {
        this.healthPoint = healthPoint;
        return this;
    }

    public ActionCommandBuilder withMaxHealthPoint(float maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
        return this;
    }

    public ActionCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ActionCommandBuilder withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public ActionCommand build() {
        return new ActionCommand(action, x, y, speedX, speedY, healthPoint, maxHealthPoint, name, direction);
    }

}
