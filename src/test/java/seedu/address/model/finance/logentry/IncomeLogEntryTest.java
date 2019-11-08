package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_CAT_FOOD;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_DESC_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_FROM_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TDATE_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TMET_LOG1;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG02;
import static seedu.address.testutil.TypicalLogEntries.LOG3;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LogEntryBuilder;

public class IncomeLogEntryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LogEntry log = new LogEntryBuilder().buildIncome();
        assertThrows(UnsupportedOperationException.class, () -> log.getCategories().remove(0));
    }

    @Test
    public void isSameLogEntry() {
        // same object -> returns true
        assertTrue(LOG02.isSameLogEntry(LOG02));

        // null -> returns false
        assertFalse(LOG02.isSameLogEntry(null));

        // different amount and description -> returns false
        LogEntry editedLog02 =
                new LogEntryBuilder(LOG02)
                        .withAmount(VALID_AMOUNT_LOG1)
                        .withDescription(VALID_DESC_LOG1)
                        .buildIncome();
        assertFalse(LOG02.isSameLogEntry(editedLog02));

        // different amount -> returns false
        editedLog02 = new LogEntryBuilder(LOG02)
                .withAmount(VALID_AMOUNT_LOG1)
                .buildIncome();
        assertFalse(LOG02.isSameLogEntry(editedLog02));
    }

    @Test
    public void equals() {
        // same values -> returns true
        LogEntry logCopy02 = new LogEntryBuilder(LOG02).buildIncome();
        assertTrue(LOG02.equals(logCopy02));

        // same object -> returns true
        assertTrue(LOG02.equals(LOG02));

        // null -> returns false
        assertFalse(LOG02.equals(null));

        // different type -> returns false
        assertFalse(LOG02.equals(5));

        // different log entry -> returns false
        assertFalse(LOG02.equals(LOG3));

        // different amount -> returns false
        LogEntry editedLog02 =
                new LogEntryBuilder(LOG02).withAmount(VALID_AMOUNT_LOG1).buildIncome();
        assertFalse(LOG02.equals(editedLog02));

        // different description -> returns false
        editedLog02 =
                new LogEntryBuilder(LOG02).withDescription(VALID_DESC_LOG1).buildIncome();
        assertFalse(LOG02.equals(editedLog02));

        // different transaction date -> returns false
        editedLog02 =
                new LogEntryBuilder(LOG02).withTransactionDate(VALID_TDATE_LOG1).buildIncome();
        assertFalse(LOG02.equals(editedLog02));

        // different transaction method -> returns false
        editedLog02 =
                new LogEntryBuilder(LOG02).withTransactionMethod(VALID_TMET_LOG1).buildIncome();
        assertFalse(LOG02.equals(editedLog02));

        // different source of income -> returns false
        editedLog02 =
                new LogEntryBuilder(LOG02).withPerson(VALID_FROM_LOG1).buildIncome();
        assertFalse(LOG02.equals(editedLog02));

        // different categories -> returns false
        editedLog02 =
                new LogEntryBuilder(LOG02).withCats(VALID_CAT_FOOD).buildIncome();
        assertFalse(LOG02.equals(editedLog02));
    }
}
