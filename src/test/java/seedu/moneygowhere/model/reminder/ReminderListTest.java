package seedu.moneygowhere.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.SCHOOL_FEE_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.reminder.exception.ReminderNotFoundException;

class ReminderListTest {

    private final ReminderList reminderList = new ReminderList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(reminderList.contains(BILL_REMINDER));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        reminderList.add(SCHOOL_FEE_REMINDER);
        assertTrue(reminderList.contains(SCHOOL_FEE_REMINDER));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.add(null));
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.remove(null));
    }

    @Test
    public void remove_reminderDoesNotExist_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> reminderList.remove(SCHOOL_FEE_REMINDER));
    }

    @Test
    public void remove_existingReminder_removesSpending() {
        reminderList.add(SCHOOL_FEE_REMINDER);
        reminderList.remove(SCHOOL_FEE_REMINDER);
        ReminderList expectedReminderList = new ReminderList();
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                reminderList.asUnmodifiableObservableList().remove(0));
    }

}
