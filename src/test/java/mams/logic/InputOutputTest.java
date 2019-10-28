package mams.logic;

import static mams.logic.CommandHistoryTest.INVALID_IO_1;
import static mams.logic.CommandHistoryTest.INVALID_IO_2;
import static mams.logic.CommandHistoryTest.VALID_IO_1;
import static mams.logic.CommandHistoryTest.VALID_IO_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class InputOutputTest {

    @Test
    public void hashCodeTest() {
        InputOutput sameIo = new InputOutput(VALID_IO_1.getInput(), VALID_IO_1.getOutput());

        // same object -> same hashcode
        assertEquals(VALID_IO_1.hashCode(), VALID_IO_1.hashCode());

        // same internal values -> same hashcode
        assertEquals(VALID_IO_1.hashCode(), sameIo.hashCode());

        // different internal values -> different hashcode
        assertNotEquals(VALID_IO_1.hashCode(), VALID_IO_2.hashCode());

        // different object types -> different hashcode
        assertNotEquals(VALID_IO_1.hashCode(), Objects.hash(0));
    }

    @Test
    public void equals() {
        InputOutput sameIo = new InputOutput(VALID_IO_1.getInput(), VALID_IO_1.getOutput());
        InputOutput slightlyDifferentIo = new InputOutput(VALID_IO_1.getInput(), INVALID_IO_1.getOutput());

        // same object -> returns true
        assertTrue(VALID_IO_1.equals(VALID_IO_1));

        // same values -> returns true
        assertTrue(VALID_IO_1.equals(sameIo));

        // different types -> returns false
        assertFalse(VALID_IO_1.equals(1));

        // null -> returns false
        assertFalse(VALID_IO_1.equals(null));

        // different internal values -> returns false
        assertFalse(VALID_IO_1.equals(slightlyDifferentIo));
        assertFalse(VALID_IO_1.equals(VALID_IO_2));
        assertFalse(VALID_IO_1.equals(INVALID_IO_1));
        assertFalse(VALID_IO_1.equals(INVALID_IO_2));
    }
}
