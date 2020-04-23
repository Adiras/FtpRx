package com.ftprx.server;

import com.ftprx.server.channel.Command;
import com.ftprx.server.command.CommandDispatcher;
import org.junit.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class CommandDispatcherTest {
    @Test
    public void shouldSupportUnknownCommand() {
//        // given
//        CommandDispatcher dispatcher = spy(new CommandDispatcher());
//        Command command = Command.CommandBuilder.aCommand()
//                .withCode("USER")
//                .withArgument("admin")
//                .build();
//
//        // when
//        dispatcher.executeCommand(command, mock(ControlConnection.class));
//
//        // then
//        assertFalse(dispatcher.hasCommandRegistered(command.getCode()));
//        verify(dispatcher, times(1)).onExecuteUnknownCommand(any(ControlConnection.class));
    }
}
