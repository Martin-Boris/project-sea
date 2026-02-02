package com.bmrt.projectsea.websocket;

import com.bmrt.projectsea.domain.GameInstance;
import com.bmrt.projectsea.domain.command.ActionCommand;
import com.bmrt.projectsea.domain.command.ShootCommand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WebSocketListenerAdapterTest {

    @Nested
    class onMessage {

        @Test
        void caseShootMessage() {
            GameInstance gameInstance = Mockito.mock(GameInstance.class);
            CommandMapper mapper = spy(new CommandMapperMock());
            WebSocketListenerAdapter wsAdapter = new WebSocketListenerAdapter(gameInstance, mapper);
            wsAdapter.onMessage(null, "SHOOT;");
            verify(mapper, times(1)).getShootCommand(Mockito.any());
            verify(gameInstance, times(1)).renderShoot(any(ShootCommand.class));
        }

        @Test
        void caseLeaveMessage() {
            GameInstance gameInstance = Mockito.mock(GameInstance.class);
            CommandMapper mapper = spy(new CommandMapperMock());
            WebSocketListenerAdapter wsAdapter = new WebSocketListenerAdapter(gameInstance, mapper);
            wsAdapter.onMessage(null, "LEAVE;");
            verify(mapper, times(1)).getCommand(Mockito.any());
            verify(gameInstance, times(1)).handleAction(any(ActionCommand.class));
        }

        @Test
        void caseJoinMessage() {
            GameInstance gameInstance = Mockito.mock(GameInstance.class);
            CommandMapper mapper = spy(new CommandMapperMock());
            WebSocketListenerAdapter wsAdapter = new WebSocketListenerAdapter(gameInstance, mapper);
            wsAdapter.onMessage(null, "JOIN;");
            verify(mapper, times(1)).getCommand(Mockito.any());
            verify(gameInstance, times(1)).handleAction(any(ActionCommand.class));
        }

        @Test
        void caseTurnMessage() {
            GameInstance gameInstance = Mockito.mock(GameInstance.class);
            CommandMapper mapper = spy(new CommandMapperMock());
            WebSocketListenerAdapter wsAdapter = new WebSocketListenerAdapter(gameInstance, mapper);
            wsAdapter.onMessage(null, "TURN;");
            verify(mapper, times(1)).getCommand(Mockito.any());
            verify(gameInstance, times(1)).handleAction(any(ActionCommand.class));
        }
    }

}
