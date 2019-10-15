package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeleteInfoTest {

    @Test
    public void delete_wrongCommandWord() {
        assertFalse(DeleteInfo.COMMAND_WORD.equals("edit"));
    }

    @Test
    public void delete_wrongInformation() {
        assertFalse(DeleteInfo.INFORMATION.equals("  "));
    }

    @Test
    public void delete_correctUsage() {
        assertTrue(DeleteInfo.USAGE.equals("delete 3"));
    }

    @Test
    public void delete_wrongOutput() {
        assertFalse(DeleteInfo.OUTPUT.isEmpty());
    }
}
