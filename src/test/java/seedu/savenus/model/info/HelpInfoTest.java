package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HelpInfoTest {

    @Test
    public void help_correctCommandWord() {
        assertTrue(HelpInfo.COMMAND_WORD.equals("help"));
    }

    @Test
    public void help_wrongInformation() {
        assertFalse(HelpInfo.INFORMATION.isEmpty());
    }

    @Test
    public void help_correctUsage() {
        assertTrue(HelpInfo.USAGE.equals("help"));
    }

    @Test
    public void help_wrongOutput() {
        assertFalse(HelpInfo.OUTPUT.isEmpty());
    }
}
