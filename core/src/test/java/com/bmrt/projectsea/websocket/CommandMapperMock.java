package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.command.ActionCommand;
import com.bmrt.projectsea.domain.command.ShootCommand;
import org.mockito.Mockito;

public class CommandMapperMock extends CommandMapper {


    private final ActionCommand actionCommand = Mockito.mock(ActionCommand.class);
    private final ShootCommand shootCommand = Mockito.mock(ShootCommand.class);

    @Override
    public ActionCommand getCommand(String[] data) {
        return actionCommand;
    }

    public ShootCommand getShootCommand(String[] data) {
        return shootCommand;
    }

}
