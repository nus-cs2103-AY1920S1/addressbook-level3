package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_CAT_FOOD;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_DESC_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TDATE_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TMET_LOG1;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG04;
import static seedu.address.testutil.TypicalLogEntries.LOG7;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LogEntryBuilder;

public class LendLogEntryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LogEntry log = new LogEntryBuilder().buildLend();
        assertThrows(UnsupportedOperationException.class, () -> log.getCategories().remove(0));
    }

    @Test
    public void isSameLogEntry() {
        // same object -> returns true
        assertTrue(LOG04.isSameLogEntry(LOG04));

        // null -> returns false
        assertFalse(LOG04.isSameLogEntry(null));

        // different amount and description -> returns false
        LogEntry editedLog04 =
                new LogEntryBuilder(LOG04)
                        .withAmount(VALID_AMOUNT_LOG2)
                        .withDescription(VALID_DESC_LOG1)
                        .buildLend();
        assertFalse(LOG04.isSameLogEntry(editedLog04));

        // different amount -> returns false
        editedLog04 = new LogEntryBuilder(LOG04)
                .withAmount(VALID_AMOUNT_LOG1)
                .buildLend();
        assertFalse(LOG04.isSameLogEntry(editedLog04));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(LOG04.equals(LOG04));

        // null -> returns false
        assertFalse(LOG04.equals(null));

        // different type -> returns false
        assertFalse(LOG04.equals(5));

        // different log entry -> returns false
        assertFalse(LOG04.equals(LOG7));

        // different amount -> returns false
        LogEntry editedLog04 =
                new LogEntryBuilder(LOG04).withAmount(VALID_AMOUNT_LOG1).buildLend();
        assertFalse(LOG04.equals(editedLog04));

        // different description -> returns false
        editedLog04 =
                new LogEntryBuilder(LOG04).withDescription(VALID_DESC_LOG1).buildLend();
        assertFalse(LOG04.equals(editedLog04));

        // different transaction date -> returns false
        editedLog04 =
                new LogEntryBuilder(LOG04).withTransactionDate(VALID_TDATE_LOG2).buildLend();
        assertFalse(LOG04.equals(editedLog04));

        // different transaction method -> returns false
        editedLog04 =
                new LogEntryBuilder(LOG04).withTransactionMethod(VALID_TMET_LOG1).buildLend();
        assertFalse(LOG04.equals(editedLog04));

        // different person lent to -> returns false
        editedLog04 =
                new LogEntryBuilder(LOG04).withPerson("AlienG").buildLend();
        assertFalse(LOG04.equals(editedLog04));

        // different categories -> returns false
        editedLog04 =
                new LogEntryBuilder(LOG04).withCats(VALID_CAT_FOOD).buildLend();
        assertFalse(LOG04.equals(editedLog04));
    }
}
