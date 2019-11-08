package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_CAT_FOOD;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_DESC_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_PLACE_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TDATE_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TMET_LOG1;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG01;
import static seedu.address.testutil.TypicalLogEntries.LOG05;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LogEntryBuilder;

public class SpendLogEntryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LogEntry log = new LogEntryBuilder().buildSpend();
        assertThrows(UnsupportedOperationException.class, () -> log.getCategories().remove(0));
    }

    @Test
    public void isSameLogEntry() {
        // same object -> returns true
        assertTrue(LOG05.isSameLogEntry(LOG05));

        // null -> returns false
        assertFalse(LOG05.isSameLogEntry(null));

        // different amount and description -> returns false
        LogEntry editedLog05 =
                new LogEntryBuilder(LOG05)
                        .withAmount(VALID_AMOUNT_LOG1)
                        .withDescription(VALID_DESC_LOG1)
                        .buildSpend();
        assertFalse(LOG05.isSameLogEntry(editedLog05));

        // different amount -> returns false
        editedLog05 = new LogEntryBuilder(LOG05)
                .withAmount(VALID_AMOUNT_LOG1)
                .buildSpend();
        assertFalse(LOG05.isSameLogEntry(editedLog05));
    }

    @Test
    public void equals() {
        // same values -> returns true
        LogEntry logCopy05 = new LogEntryBuilder(LOG05).buildSpend();
        assertTrue(LOG05.equals(logCopy05));

        // same object -> returns true
        assertTrue(LOG05.equals(LOG05));

        // null -> returns false
        assertFalse(LOG05.equals(null));

        // different type -> returns false
        assertFalse(LOG05.equals(5));

        // different log entry -> returns false
        assertFalse(LOG05.equals(LOG01));

        // different amount -> returns false
        LogEntry editedLog05 =
                new LogEntryBuilder(LOG05).withAmount(VALID_AMOUNT_LOG1).buildSpend();
        assertFalse(LOG05.equals(editedLog05));

        // different description -> returns false
        editedLog05 =
                new LogEntryBuilder(LOG05).withDescription(VALID_DESC_LOG1).buildSpend();
        assertFalse(LOG05.equals(editedLog05));

        // different transaction date -> returns false
        editedLog05 =
                new LogEntryBuilder(LOG05).withTransactionDate(VALID_TDATE_LOG1).buildSpend();
        assertFalse(LOG05.equals(editedLog05));

        // different transaction method -> returns false
        editedLog05 =
                new LogEntryBuilder(LOG05).withTransactionMethod(VALID_TMET_LOG1).buildSpend();
        assertFalse(LOG05.equals(editedLog05));

        // different place -> returns false
        editedLog05 =
                new LogEntryBuilder(LOG05).withPlace(VALID_PLACE_LOG1).buildSpend();
        assertFalse(LOG05.equals(editedLog05));

        // different categories -> returns false
        editedLog05 =
                new LogEntryBuilder(LOG05).withCats(VALID_CAT_FOOD).buildSpend();
        assertFalse(LOG05.equals(editedLog05));
    }
}
