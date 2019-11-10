package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class HistoryTest {

    @Test
    public void testHistoryFunctionality() {
        History historyTest = new History();

        // test history when empty
        assertThrows(NoSuchElementException.class, () -> historyTest.getNextCommand());
        assertThrows(NoSuchElementException.class, () -> historyTest.getPastCommand());

        // test when there is history
        historyTest.sendToHistory("test");
        assertEquals(historyTest.getPastCommand(), historyTest.getNextCommand());
        historyTest.sendToHistory("test2");
        assertEquals("test2", historyTest.getPastCommand());
        assertEquals("test", historyTest.getPastCommand());
        assertEquals("test", historyTest.getNextCommand());
        assertEquals("test2", historyTest.getNextCommand());
    }
}
