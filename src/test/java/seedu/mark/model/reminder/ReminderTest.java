package seedu.mark.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_READ;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_READ;
import static seedu.mark.testutil.TypicalBookmarks.*;
import static seedu.mark.testutil.TypicalReminders.OPEN;
import static seedu.mark.testutil.TypicalReminders.READ;

import org.junit.jupiter.api.Test;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.ReminderBuilder;



class ReminderTest {

    @Test
    public void isSameBookmark() {
        // same object -> returns true
        assertTrue(OPEN.isSameReminder(OPEN));

        // null -> returns false
        assertFalse(OPEN.isSameReminder(null));

        // different note, different bookmark, different time-> returns false
        Reminder editedOPEN = new ReminderBuilder(OPEN).withNote(VALID_NOTE_READ)
                .withBookmark(BENSON).withTime(VALID_TIME_READ).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));

        // same note, different bookmark, same time-> returns false
        editedOPEN = new ReminderBuilder(OPEN).withNote(VALID_NOTE_OPEN)
                .withBookmark(BENSON).withTime(VALID_TIME_OPEN).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));

        // different note, same bookmark, same time -> returns false
        editedOPEN = new ReminderBuilder(OPEN).withNote(VALID_NOTE_READ)
                .withBookmark(ALICE).withTime(VALID_TIME_OPEN).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));

        // same note, same bookmark, different time -> returns false
        editedOPEN = new ReminderBuilder(OPEN).withNote(VALID_NOTE_OPEN)
                .withBookmark(ALICE).withTime(VALID_TIME_READ).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));

        //same attributes -> returns true
        editedOPEN = new ReminderBuilder(OPEN).withNote(VALID_NOTE_OPEN)
                .withBookmark(ALICE).withTime(VALID_TIME_OPEN).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder openCopy = new ReminderBuilder(OPEN).build();
        assertTrue(OPEN.equals(openCopy));

        openCopy = new ReminderBuilder(OPEN).withBookmark(ALICE).withTime(VALID_TIME_OPEN)
                .withNote(VALID_NOTE_OPEN).build();
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
        Reminder editedOPEN = new ReminderBuilder(OPEN).withNote(VALID_NOTE_READ).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));

        // different bookmark -> returns false
        editedOPEN = new ReminderBuilder(OPEN).withBookmark(BENSON).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));

        // different time -> returns false
        editedOPEN = new ReminderBuilder(OPEN).withTime(VALID_TIME_READ).build();
        assertFalse(OPEN.isSameReminder(editedOPEN));
    }

}