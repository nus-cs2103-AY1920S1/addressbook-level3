package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AddInfoTest {

    @Test
    public void add_correctCommandWord() {
        assertTrue(AddInfo.COMMAND_WORD.equals("add"));
    }

    @Test
    public void add_wrongCommandWord() {
        assertFalse(AddInfo.COMMAND_WORD.equals("edit"));
    }

    @Test
    public void add_wrongInformation() {
        assertFalse(AddInfo.INFORMATION.isEmpty());
    }

    @Test
    public void add_correctOutput() {
        assertTrue(AddInfo.OUTPUT.contains("name"));
    }
}
