package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ListInfoTest {

    @Test
    public void list_correctCommandWord() {
        assertTrue(ListInfo.COMMAND_WORD.equals("list"));
    }

    @Test
    public void list_wrongInformation() {
        assertFalse(ListInfo.INFORMATION.isEmpty());
    }

    @Test
    public void list_correctUsage() {
        assertTrue(ListInfo.USAGE.equals("list"));
    }

    @Test
    public void list_wrongOutput() {
        assertFalse(ListInfo.OUTPUT.isEmpty());
    }
}
