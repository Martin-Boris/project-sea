package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.ActionCommand;
import com.bmrt.projectsea.domain.ActionCommandBuilder;
import com.bmrt.projectsea.domain.Direction;

public class CommandMapper {

    public ActionCommand getCommand(String packet) {
        //TODO improve memory management
        String[] data = packet.split(";");
        ActionCommand actionCommand = ActionCommandBuilder.anActionCommand()
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
        return actionCommand;
    }
}
