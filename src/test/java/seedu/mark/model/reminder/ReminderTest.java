package seedu.mark.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_READ;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_READ;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.mark.testutil.TypicalReminders.OPEN;
import static seedu.mark.testutil.TypicalReminders.READ;

import org.junit.jupiter.api.Test;

import seedu.mark.testutil.ReminderBuilder;

class ReminderTest {

    @Test
    public void isSameBookmark() {
        // same object -> returns true
        assertTrue(OPEN.isSameReminder(OPEN));

        // null -> returns false
        assertFalse(OPEN.isSameReminder(null));

        // different note, different url, different time-> returns false
        Reminder editedOpen = new ReminderBuilder(OPEN).withNote(VALID_NOTE_READ)
                .withUrl(VALID_URL_BOB).withTime(VALID_TIME_READ).build();
        assertFalse(OPEN.isSameReminder(editedOpen));

        // same note, different url, same time-> returns false
        editedOpen = new ReminderBuilder(OPEN).withNote(VALID_NOTE_OPEN)
                .withUrl(VALID_URL_BOB).withTime(VALID_TIME_OPEN).build();
        assertFalse(OPEN.isSameReminder(editedOpen));

        // different note, same url, same time -> returns false
        editedOpen = new ReminderBuilder(OPEN).withNote(VALID_NOTE_READ)
                .withUrl(OPEN.getUrl().toString()).withTime(VALID_TIME_OPEN).build();
        assertFalse(OPEN.isSameReminder(editedOpen));

        // same note, same url, different time -> returns false
        editedOpen = new ReminderBuilder(OPEN).withNote(VALID_NOTE_OPEN)
                .withUrl(OPEN.getUrl().toString()).withTime(VALID_TIME_READ).build();
        assertFalse(OPEN.isSameReminder(editedOpen));

        //same attributes -> returns true
        editedOpen = new ReminderBuilder(OPEN).withNote(VALID_NOTE_OPEN)
                .withUrl(OPEN.getUrl().toString()).withTime(VALID_TIME_OPEN).build();
        assertFalse(OPEN.isSameReminder(editedOpen));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder openCopy = new ReminderBuilder(OPEN).build();
        assertTrue(OPEN.equals(openCopy));

        // same object -> returns true
        assertTrue(OPEN.equals(OPEN));

        // null -> returns false
        assertFalse(OPEN.equals(null));

        // different type -> returns false
        assertFalse(OPEN.equals(5));

        // different reminder -> returns false
        assertFalse(OPEN.equals(READ));

        // different note -> returns false
        Reminder editedOpen = new ReminderBuilder(OPEN).withNote(VALID_NOTE_READ).build();
        assertFalse(OPEN.isSameReminder(editedOpen));

        // different url -> returns false
        editedOpen = new ReminderBuilder(OPEN).withUrl(VALID_URL_BOB).build();
        assertFalse(OPEN.isSameReminder(editedOpen));

        // different time -> returns false
        editedOpen = new ReminderBuilder(OPEN).withTime(VALID_TIME_READ).build();
        assertFalse(OPEN.isSameReminder(editedOpen));
    }
}
