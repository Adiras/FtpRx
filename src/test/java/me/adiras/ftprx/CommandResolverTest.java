package me.adiras.ftprx;

import org.junit.Test;

import static org.junit.Assert.*;
import static me.adiras.ftprx.CommandResolver.*;

public class CommandResolverTest {

    @Test
    public void shouldReturnNormalizedString() {
        assertEquals(normalize("<username>"), "[\\x00-\\x7F&&[^\\x0D\\x0A]]*");
        assertEquals(normalize("<password>"), "[\\x00-\\x7F&&[^\\x0D\\x0A]]*");
        assertEquals(normalize("<account-information>"), "[\\x00-\\x7F&&[^\\x0D\\x0A]]*");
        assertEquals(normalize("<string>"), "[\\x00-\\x7F&&[^\\x0D\\x0A]]*");
        assertEquals(normalize("<char>"), "[\\x00-\\x7F&&[^\\x0D\\x0A]]");
        assertEquals(normalize("<marker>"), "[\\x21-7e]*");
        assertEquals(normalize("<pr-string>"), "[\\x21-7e]*");
        assertEquals(normalize("<pr-char>"), "[\\x21-7e]");
        assertEquals(normalize("<byte-size>"), "[1-255]");
        assertEquals(normalize("<host-port>"), "[1-255],[1-255],[1-255],[1-255],[1-255],[1-255],[1-255],[1-255]");
        assertEquals(normalize("<host-number>"), "[1-255],[1-255],[1-255],[1-255]");
        assertEquals(normalize("<port-number>"), "[1-255],[1-255]");
        assertEquals(normalize("<number>"), "[1-255]");
        assertEquals(normalize("<form-code>"), "[NTC]");
        assertEquals(normalize("<structure-code>"), "[FRP]");
        assertEquals(normalize("<mode-code>"), "[SBC]");
        assertEquals(normalize("<pathname>"), "[\\x00-\\x7F&&[^\\x0D\\x0A]]*");
        assertEquals(normalize("<decimal-integer>"), "[0-9]+(.[0-9]*)");
    }

    @Test
    public void shouldReturnUserCommand() {
        assertEquals(resolve("USER \r\n"), "USER");
        assertEquals(resolve("USER \r\n"), "USER");
        assertEquals(resolve("USER username\r\n"), "USER");
        assertEquals(resolve("USER username username\r\n"), "USER");
        assertEquals(resolve("USER         \r\n"), "USER");
        assertNull(resolve("USER"));
        assertNull(resolve("USER\r"));
        assertNull(resolve("USER\n"));
        assertNull(resolve("USER\r\n"));
        assertNull(resolve("USER-username\r\n"));
    }
}
