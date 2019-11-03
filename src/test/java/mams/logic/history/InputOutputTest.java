package mams.logic.history;

import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_1;
import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_2;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_IO_1;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_IO_2;
import static mams.testutil.TypicalTimeStamps.TIME_STAMP_3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class InputOutputTest {

    @Test
    public void hashCodeTest() {
        InputOutput sameIo = new InputOutput(SUCCESSFUL_IO_1.getInput(), SUCCESSFUL_IO_1.getOutput(),
                SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp());

        // same object -> same hashcode
        assertEquals(SUCCESSFUL_IO_1.hashCode(), SUCCESSFUL_IO_1.hashCode());

        // same internal values -> same hashcode
        assertEquals(SUCCESSFUL_IO_1.hashCode(), sameIo.hashCode());

        // different internal values -> different hashcode
        assertNotEquals(SUCCESSFUL_IO_1.hashCode(), SUCCESSFUL_IO_2.hashCode());

        // different object types -> different hashcode
        assertNotEquals(SUCCESSFUL_IO_1.hashCode(), Objects.hash(0));
    }

    @Test
    public void equals() {
        InputOutput sameIo = new InputOutput(SUCCESSFUL_IO_1.getInput(), SUCCESSFUL_IO_1.getOutput(),
                SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp());
        InputOutput slightlyDifferentIo = new InputOutput(SUCCESSFUL_IO_1.getInput(), SUCCESSFUL_IO_1.getOutput(),
                SUCCESSFUL_IO_1.checkSuccessful(), TIME_STAMP_3);

        // same object -> returns true
        assertTrue(SUCCESSFUL_IO_1.equals(SUCCESSFUL_IO_1));

        // same values -> returns true
        assertTrue(SUCCESSFUL_IO_1.equals(sameIo));

        // different types -> returns false
        assertFalse(SUCCESSFUL_IO_1.equals(1));

        // null -> returns false
        assertFalse(SUCCESSFUL_IO_1.equals(null));

        // different internal values -> returns false
        assertFalse(SUCCESSFUL_IO_1.equals(slightlyDifferentIo));
        assertFalse(SUCCESSFUL_IO_1.equals(SUCCESSFUL_IO_2));
        assertFalse(SUCCESSFUL_IO_1.equals(UNSUCCESSFUL_IO_1));
        assertFalse(SUCCESSFUL_IO_1.equals(UNSUCCESSFUL_IO_2));
    }
}
