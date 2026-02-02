package com.bmrt.projectsea.domain.command;

public class ShootCommandBuilder {

    private float healthPoint;
    private float maxHealthPoint;
    private String targetName;
    private String shooterName;

    private ShootCommandBuilder() {
    }

    public static ShootCommandBuilder aShootCommand() {
        return new ShootCommandBuilder();
    }

    public ShootCommandBuilder withHealthPoint(float healthPoint) {
        this.healthPoint = healthPoint;
        return this;
    }

    public ShootCommandBuilder withMaxHealthPoint(float maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
        return this;
    }

    public ShootCommandBuilder withTargetName(String name) {
        this.targetName = name;
        return this;
    }

    public ShootCommandBuilder withShooterName(String name) {
        this.shooterName = name;
        return this;
    }

    public ShootCommand build() {
        return new ShootCommand(healthPoint, maxHealthPoint, targetName, shooterName);
    }

}
