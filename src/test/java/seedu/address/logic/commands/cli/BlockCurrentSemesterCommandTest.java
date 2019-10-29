package seedu.address.logic.commands.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.semester.SemesterName;

class BlockCurrentSemesterCommandTest {
    @Test
    public void constructor_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BlockCurrentSemesterCommand(null, "LOA"));
    }

    @Test
    public void equals() {
        BlockCurrentSemesterCommand blockSemesterCommand =
                new BlockCurrentSemesterCommand(SemesterName.Y1S1, "LOA");
        BlockCurrentSemesterCommand otherBlockCurrentSemesterCommand =
                new BlockCurrentSemesterCommand(SemesterName.Y1S2, "EXCHANGE");

        // same object -> returns true
        assertTrue(blockSemesterCommand.equals(blockSemesterCommand));

        // same values -> returns true
        BlockCurrentSemesterCommand blockSemesterCommandCopy =
                new BlockCurrentSemesterCommand(SemesterName.Y1S1, "LOA");
        assertTrue(blockSemesterCommand.equals(blockSemesterCommandCopy));

        // different types -> returns false
        assertFalse(blockSemesterCommand.equals(1));

        // null -> returns false
        assertFalse(blockSemesterCommand.equals(null));

        // different module code -> returns false
        assertFalse(blockSemesterCommand.equals(otherBlockCurrentSemesterCommand));
    }
}
