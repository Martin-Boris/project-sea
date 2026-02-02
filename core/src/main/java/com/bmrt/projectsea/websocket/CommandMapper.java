package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.command.ActionCommand;
import com.bmrt.projectsea.domain.command.ActionCommandBuilder;
import com.bmrt.projectsea.domain.command.ShootCommand;
import com.bmrt.projectsea.domain.command.ShootCommandBuilder;

public class CommandMapper {

    public ActionCommand getCommand(String[] data) {
        //TODO improve memory management
        return ActionCommandBuilder.anActionCommand()
            .withAction(Action.valueOf(data[0]))
            .withX(Float.parseFloat(data[1]))
            .withY(Float.parseFloat(data[2]))
            .withSpeedX(Float.parseFloat(data[3]))
            .withSpeedY(Float.parseFloat(data[4]))
            .withDirection(Direction.valueOf(data[5]))
            .withName(data[6])
            .withHealthPoint(Float.parseFloat(data[7]))
            .withMaxHealthPoint(Float.parseFloat(data[8]))
            .build();
    }

    public ShootCommand getShootCommand(String[] data) {
        return ShootCommandBuilder.aShootCommand()
            .withTargetName(data[1])
            .withHealthPoint(Float.parseFloat(data[2]))
            .withMaxHealthPoint(Float.parseFloat(data[3]))
            .withShooterName(data[4])
            .build();
    }
}
