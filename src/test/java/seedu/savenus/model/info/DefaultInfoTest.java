package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.DefaultCommand;

public class DefaultInfoTest {

    @Test
    public void default_correctCommandWord() {
        assertTrue(DefaultInfo.COMMAND_WORD.equals(DefaultCommand.COMMAND_WORD));
    }

    @Test
    public void default_wrongInformation() {
        assertFalse(DefaultInfo.INFORMATION.equals(""));
    }

    @Test
    public void default_wrongUsage() {
        assertFalse(DefaultInfo.USAGE.isEmpty());
    }

    @Test
    public void default_emptyWrongOutput() {
        assertTrue(!DefaultInfo.OUTPUT.isEmpty());
    }
}
