package seedu.address.model.calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalReminders.BUY_INSULIN;
import static seedu.address.testutil.TypicalReminders.DATE_DEC20;
import static seedu.address.testutil.TypicalReminders.DATE_JAN04;
import static seedu.address.testutil.TypicalReminders.LUNCH_INSULIN_INJECTION;
import static seedu.address.testutil.TypicalReminders.ONCE;
import static seedu.address.testutil.TypicalReminders.TIME_EVEN;
import static seedu.address.testutil.TypicalReminders.TIME_MORN;
import static seedu.address.testutil.TypicalReminders.WEEKLY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReminderBuilder;

public class ReminderTest {

    @Test
    public void isSamePersonReminder() {
        // same object -> returns true
        assertTrue(LUNCH_INSULIN_INJECTION.isSameReminder(LUNCH_INSULIN_INJECTION));

        // null -> returns false
        assertFalse(LUNCH_INSULIN_INJECTION.isSameReminder(null));

        // different description and date, time -> returns false
        Reminder editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withDescription("description")
                .withDateTime(DATE_DEC20, TIME_MORN).build();
        assertFalse(LUNCH_INSULIN_INJECTION.isSameReminder(editedReminder));

        // different date and time -> returns false
        editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withDateTime(DATE_JAN04, TIME_EVEN).build();
        assertFalse(LUNCH_INSULIN_INJECTION.isSameReminder(editedReminder));

        // different description
        editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withDescription("description").build();
        assertFalse(LUNCH_INSULIN_INJECTION.isSameReminder(editedReminder));

        // same description and date, time, different repetition -> returns true
        editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withRepetition(WEEKLY).build();
        assertTrue(LUNCH_INSULIN_INJECTION.isSameReminder(editedReminder));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder lunchInsulinInjectionCopy = new ReminderBuilder(LUNCH_INSULIN_INJECTION).build();
        assertTrue(LUNCH_INSULIN_INJECTION.equals(lunchInsulinInjectionCopy));

        // same object -> returns true
        assertTrue(LUNCH_INSULIN_INJECTION.equals(LUNCH_INSULIN_INJECTION));

        // null -> returns false
        assertFalse(LUNCH_INSULIN_INJECTION.equals(null));

        // different type -> returns false
        assertFalse(LUNCH_INSULIN_INJECTION.equals(5));

        // different reminder -> returns false
        assertFalse(LUNCH_INSULIN_INJECTION.equals(BUY_INSULIN));

        // different description -> returns false
        Reminder editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withDescription("description").build();
        assertFalse(LUNCH_INSULIN_INJECTION.equals(editedReminder));

        // different date, time -> returns false
        editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withDateTime(DATE_DEC20, TIME_EVEN).build();
        assertFalse(LUNCH_INSULIN_INJECTION.equals(editedReminder));

        // different repetition -> returns false
        editedReminder = new ReminderBuilder(LUNCH_INSULIN_INJECTION).withRepetition(ONCE).build();
        assertFalse(LUNCH_INSULIN_INJECTION.equals(editedReminder));
    }
}
