package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.ActionCommand;
import com.bmrt.projectsea.domain.ActionCommandBuilder;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;
import com.bmrt.projectsea.domain.Vector;

public class CommandMapper {

    public void updateShip(String[] data, Ship ship) {
        ship.setPosition(Float.parseFloat(data[1]), Float.parseFloat(data[2]));
        ship.setSpeed(Float.parseFloat(data[3]), Float.parseFloat(data[4]));
        ship.setDirection(Direction.valueOf(data[5]));
        ship.setHealthPoint(Float.parseFloat(data[7]));
        ship.setMaxHealthPoint(Float.parseFloat(data[8]));
    }

    public Ship createShip(String[] data) {
        return new Ship(
            new Vector(Float.parseFloat(data[1]), Float.parseFloat(data[2])),
            new Vector(Float.parseFloat(data[3]), Float.parseFloat(data[4])),
            Direction.valueOf(data[5]),
            data[6],
            Float.parseFloat(data[7]),
            Float.parseFloat(data[8])
        );
    }

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
