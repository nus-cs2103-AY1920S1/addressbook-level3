package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DefaultCommandTest {
    @Test
    public void equals() {
        DefaultCommand test = new DefaultCommand();
        assertEquals(test, test);
        assertEquals(test, new DefaultCommand());
        assertEquals(new DefaultCommand(), new DefaultCommand());
    }
}
