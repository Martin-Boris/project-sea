package com.bmrt.projectsea.domain.command;

import com.bmrt.projectsea.websocket.Action;

import java.util.Objects;

public class ShootCommand {

    private final float healthPoint;
    private final float maxHealthPoint;
    private final String targetName;
    private final String shooterName;

    public ShootCommand(float healthPoint, float maxHealthPoint, String targetName, String shooterName) {
        this.healthPoint = healthPoint;
        this.maxHealthPoint = maxHealthPoint;
        this.targetName = targetName;
        this.shooterName = shooterName;
    }

    public Action getAction() {
        return Action.SHOOT;
    }

    public float getHealthPoint() {
        return healthPoint;
    }

    public float getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getShooterName() {
        return shooterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShootCommand that = (ShootCommand) o;
        return Float.compare(healthPoint, that.healthPoint) == 0 && Float.compare(maxHealthPoint, that.maxHealthPoint) == 0 && Objects.equals(targetName, that.targetName) && Objects.equals(shooterName, that.shooterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(healthPoint, maxHealthPoint, targetName, shooterName);
    }
}
