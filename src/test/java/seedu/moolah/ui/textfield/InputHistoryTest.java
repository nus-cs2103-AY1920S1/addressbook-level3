package seedu.moolah.ui.textfield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InputHistoryTest {

    private static InputHistory SUT_1;
    private static InputHistory SUT_2;

    private static final String INPUT_1 = "asdas 1 2312edfed1 11 ";
    private static final String INPUT_2 = "asdas 1 23*!(YC@ Hd1 11 ";

    @BeforeAll
    static void temp() {
        SUT_1 = new InputHistory();
        SUT_1.push(INPUT_1);

        SUT_2 = new InputHistory();
        SUT_2.push(INPUT_1);
        SUT_2.push(INPUT_2);
    }

    @Test
    void getPreviousInput() {
        assertEquals(INPUT_2, SUT_2.getPreviousInput());
        assertEquals(INPUT_1, SUT_2.getPreviousInput());
        assertThrows(NoSuchElementException.class, () -> SUT_2.getPreviousInput());

        assertEquals(INPUT_1, SUT_1.getPreviousInput());
        assertThrows(NoSuchElementException.class, () -> SUT_1.getPreviousInput());
    }

    @Test
    void getNextInput() {
        try {
            SUT_2.getPreviousInput();
            SUT_2.getPreviousInput();
            SUT_2.getPreviousInput();
        } catch (NoSuchElementException e) {
            assertEquals(INPUT_1, SUT_2.getNextInput());
            assertEquals(INPUT_2, SUT_2.getNextInput());
            assertThrows(NoSuchElementException.class, () -> SUT_2.getNextInput());
        }

        try {
            SUT_1.getPreviousInput();
            SUT_1.getPreviousInput();
        } catch (NoSuchElementException e) {
            assertEquals(INPUT_1, SUT_1.getNextInput());
            assertThrows(NoSuchElementException.class, () -> SUT_1.getNextInput());
        }
    }
}