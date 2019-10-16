package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExitInfoTest {

    @Test
    public void exitInfo_differentClass() {
        ExitInfo exitInfo = new ExitInfo();
        assertFalse(exitInfo.equals(new ExitInfo()));
    }

    @Test
    public void exit_correctCommandWord() {
        assertTrue(ExitInfo.COMMAND_WORD.equals("exit"));
    }

    @Test
    public void exit_wrongInformation() {
        assertFalse(ExitInfo.INFORMATION.isEmpty());
    }

    @Test
    public void exit_correctUsage() {
        assertTrue(ExitInfo.USAGE.equals("exit"));
    }

    @Test
    public void exit_correctOutput() {
        assertTrue(ExitInfo.OUTPUT.equals("The app will close."));
    }
}
