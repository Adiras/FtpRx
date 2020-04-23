package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void valueOfTest() {
        Command cmd;

        cmd = Command.valueOf("USER\r\n");
        assertEquals("USER", cmd.getCode());
        assertEquals(0, cmd.getArguments().size());

        cmd = Command.valueOf("USER admin\r\n");
        assertEquals("USER", cmd.getCode());
        assertEquals("admin", cmd.getArgument(0));

        cmd = Command.valueOf("MKD C:test\r\n");
        assertEquals("MKD", cmd.getCode());
        assertEquals("C:test", cmd.getArgument(0));

        cmd = Command.valueOf(" MKD C:test\r\n");
        assertEquals("MKD", cmd.getCode());
        assertEquals("C:test", cmd.getArgument(0));
    }
}
