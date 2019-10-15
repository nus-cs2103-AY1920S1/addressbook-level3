package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EditInfoTest {

    @Test
    public void edit_correctCommandWord() {
        assertTrue(EditInfo.COMMAND_WORD.equals("edit"));
    }

    @Test
    public void edit_wrongInformation() {
        assertFalse(EditInfo.INFORMATION.isEmpty());
    }

    @Test
    public void edit_correctUsage() {
        assertTrue(EditInfo.USAGE.contains("edit"));
    }

    @Test
    public void edit_wrongOutput() {
        assertFalse(EditInfo.OUTPUT.equals("nothing"));
    }
}
