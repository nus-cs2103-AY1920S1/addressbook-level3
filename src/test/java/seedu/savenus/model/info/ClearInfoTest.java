package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClearInfoTest {

    @Test
    public void clear_correctCommandWord() {
        assertTrue(ClearInfo.COMMAND_WORD.equals("clear"));
    }

    @Test
    public void clear_wrongInformation() {
        assertFalse(ClearInfo.INFORMATION.equals(""));
    }

    @Test
    public void clear_correctUsage() {
        assertTrue(ClearInfo.USAGE.equals("clear"));
    }

    @Test
    public void clear_emptyWrongOutput() {
        assertFalse(ClearInfo.OUTPUT.isEmpty());
    }
}
