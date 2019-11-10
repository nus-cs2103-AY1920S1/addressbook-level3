package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Contains tests for {@code InputHistory}.
 */
class InputHistoryTest {

    private static final String INPUT_1 = "asdas 1 2312edfed1 11 ";
    private static final String INPUT_2 = "asdas 1 23*!(YC@ Hd1 11 ";
    private static InputHistory inputHistory1;
    private static InputHistory inputHistory2;

    @BeforeAll
    static void populateHistory() {
        inputHistory1 = new InputHistory();
        inputHistory1.push(INPUT_1);

        inputHistory2 = new InputHistory();
        inputHistory2.push(INPUT_1);
        inputHistory2.push(INPUT_2);
    }

    @Test
    void getNextAndPreviousInput() {
        assertEquals(INPUT_2, inputHistory2.getPreviousInput());
        assertEquals(INPUT_1, inputHistory2.getPreviousInput());
        assertThrows(NoSuchElementException.class, () -> inputHistory2.getPreviousInput());
        assertEquals(INPUT_1, inputHistory2.getNextInput());
        assertEquals(INPUT_2, inputHistory2.getNextInput());
        assertThrows(NoSuchElementException.class, () -> inputHistory2.getNextInput());

        assertEquals(INPUT_1, inputHistory1.getPreviousInput());
        assertThrows(NoSuchElementException.class, () -> inputHistory1.getPreviousInput());
        assertEquals(INPUT_1, inputHistory1.getNextInput());
        assertThrows(NoSuchElementException.class, () -> inputHistory1.getNextInput());
    }
}
