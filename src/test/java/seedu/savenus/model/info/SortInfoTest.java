package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SortInfoTest {

    @Test
    public void sort_correctCommandWord() {
        assertTrue(SortInfo.COMMAND_WORD.equals("sort"));
    }

    @Test
    public void sort_wrongInformation() {
        assertFalse(SortInfo.INFORMATION.isEmpty());
    }

    @Test
    public void sort_correctUsage() {
        assertTrue(SortInfo.USAGE.contains("sort"));
    }

    @Test
    public void sort_wrongOutput() {
        assertFalse(SortInfo.OUTPUT.isEmpty());
    }
}
