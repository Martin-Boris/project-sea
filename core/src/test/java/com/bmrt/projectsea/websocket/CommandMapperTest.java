package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.command.ActionCommand;
import com.bmrt.projectsea.domain.command.ActionCommandBuilder;
import com.bmrt.projectsea.domain.command.ShootCommand;
import com.bmrt.projectsea.domain.command.ShootCommandBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandMapperTest {
    @Test
    void caseJoin() {
        String[] message = "JOIN;10.0;11.0;0.0;1.0;BOT;Test;9000.0;10000.0".split(";");
        CommandMapper commandMapper = new CommandMapper();
        ActionCommand command = commandMapper.getCommand(message);
        ActionCommand expectedCmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.JOIN)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(1.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Assertions.assertEquals(command, expectedCmd);
    }

    @Test
    void caseLeave() {
        String[] message = "LEAVE;10.0;11.0;0.0;1.0;BOT;Test;9000.0;10000.0".split(";");
        CommandMapper commandMapper = new CommandMapper();
        ActionCommand command = commandMapper.getCommand(message);
        ActionCommand expectedCmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.LEAVE)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(1.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Assertions.assertEquals(command, expectedCmd);
    }

    @Test
    void caseTurn() {
        String[] message = "TURN;10.0;11.0;0.0;1.0;BOT;Test;9000.0;10000.0".split(";");
        CommandMapper commandMapper = new CommandMapper();
        ActionCommand command = commandMapper.getCommand(message);
        ActionCommand expectedCmd = ActionCommandBuilder.anActionCommand()
            .withAction(Action.TURN)
            .withX(10.0f)
            .withY(11.0f)
            .withSpeedX(0.0f)
            .withSpeedY(1.0f)
            .withHealthPoint(9000.0f)
            .withMaxHealthPoint(10000.0f)
            .withName("Test")
            .withDirection(Direction.BOT)
            .build();
        Assertions.assertEquals(command, expectedCmd);
    }

    @Test
    void caseShoot() {
        String[] message = "SHOOT;target;7500.0;10000.0;shooter".split(";");
        CommandMapper commandMapper = new CommandMapper();
        ShootCommand command = commandMapper.getShootCommand(message);
        ShootCommand expectedCmd = ShootCommandBuilder.aShootCommand()
            .withHealthPoint(7500.0f)
            .withMaxHealthPoint(10000.0f)
            .withTargetName("target")
            .withShooterName("shooter")
            .build();
        Assertions.assertEquals(command, expectedCmd);
    }


}
