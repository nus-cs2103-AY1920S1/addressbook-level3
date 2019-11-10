package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_CAT_FOOD;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_DESC_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TDATE_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TMET_LOG1;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG03;
import static seedu.address.testutil.TypicalLogEntries.LOG5;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LogEntryBuilder;

public class BorrowLogEntryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LogEntry log = new LogEntryBuilder().buildBorrow();
        assertThrows(UnsupportedOperationException.class, () -> log.getCategories().remove(0));
    }

    @Test
    public void isSameLogEntry() {
        // same object -> returns true
        assertTrue(LOG03.isSameLogEntry(LOG03));

        // null -> returns false
        assertFalse(LOG03.isSameLogEntry(null));

        // different amount and description -> returns false
        LogEntry editedLog03 =
                new LogEntryBuilder(LOG03)
                        .withAmount(VALID_AMOUNT_LOG2)
                        .withDescription(VALID_DESC_LOG1)
                        .buildBorrow();
        assertFalse(LOG03.isSameLogEntry(editedLog03));

        // different amount -> returns false
        editedLog03 = new LogEntryBuilder(LOG03)
                .withAmount(VALID_AMOUNT_LOG2)
                .buildBorrow();
        assertFalse(LOG03.isSameLogEntry(editedLog03));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(LOG03.equals(LOG03));

        // null -> returns false
        assertFalse(LOG03.equals(null));

        // different type -> returns false
        assertFalse(LOG03.equals(5));

        // different log entry -> returns false
        assertFalse(LOG03.equals(LOG5));

        // different amount -> returns false
        LogEntry editedLog03 =
                new LogEntryBuilder(LOG03).withAmount(VALID_AMOUNT_LOG2).buildBorrow();
        assertFalse(LOG03.equals(editedLog03));

        // different description -> returns false
        editedLog03 =
                new LogEntryBuilder(LOG03).withDescription(VALID_DESC_LOG1).buildBorrow();
        assertFalse(LOG03.equals(editedLog03));

        // different transaction date -> returns false
        editedLog03 =
                new LogEntryBuilder(LOG03).withTransactionDate(VALID_TDATE_LOG2).buildBorrow();
        assertFalse(LOG03.equals(editedLog03));

        // different transaction method -> returns false
        editedLog03 =
                new LogEntryBuilder(LOG03).withTransactionMethod(VALID_TMET_LOG1).buildBorrow();
        assertFalse(LOG03.equals(editedLog03));

        // different person borrowed from -> returns false
        editedLog03 =
                new LogEntryBuilder(LOG03).withPerson("AlienG").buildBorrow();
        assertFalse(LOG03.equals(editedLog03));

        // different categories -> returns false
        editedLog03 =
                new LogEntryBuilder(LOG03).withCats(VALID_CAT_FOOD).buildBorrow();
        assertFalse(LOG03.equals(editedLog03));
    }
}
