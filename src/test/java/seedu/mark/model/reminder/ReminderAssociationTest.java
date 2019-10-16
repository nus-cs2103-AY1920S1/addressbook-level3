package seedu.mark.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalReminders.OPEN;
import static seedu.mark.testutil.TypicalReminders.READ;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.exceptions.BookmarkContainNoReminderException;
import seedu.mark.model.bookmark.exceptions.ExistReminderException;
import seedu.mark.model.bookmark.exceptions.ReminderNotFoundException;
import seedu.mark.testutil.ReminderBuilder;



class ReminderAssociationTest {
    //TODO: Change test method name
    private final ReminderAssociation association = new ReminderAssociation();

    @Test
    public void contains_nullBookmark_throwsNullPointerException() {
        assertFalse(association.isBookmarkHasReminder(null));
    }

    @Test
    public void contains_bookmarkNotInAssociation_returnsFalse() {
        assertFalse(association.isBookmarkHasReminder(ALICE));
    }

    @Test
    public void contains_bookmarkInAssociation_returnsTrue() {
        association.addReminder(ALICE, OPEN);
        assertTrue(association.isBookmarkHasReminder(ALICE));
    }

    @Test
    public void add_duplicateBookmark_throwsExistReminderException() {
        association.addReminder(ALICE, OPEN);
        assertThrows(ExistReminderException.class, () -> association.addReminder(ALICE, READ));
    }

    @Test
    public void setBookmark_nullEditedBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> association.setReminder(OPEN, null));
    }

    @Test
    public void setBookmark_notExistBookmark_throwsBookmarkContainNoReminderException() {
        assertThrows(BookmarkContainNoReminderException.class, () -> association.setReminder(READ, OPEN));
    }

    @Test
    public void setBookmark_notExistReminderOfSpecifiedBookmark_throwsReminderNotFoundException() {
        association.addReminder(ALICE, OPEN);
        Reminder reminder = new ReminderBuilder().withNote("Read").build();
        assertThrows(ReminderNotFoundException.class, () -> association.setReminder(reminder, OPEN));
    }

    @Test
    public void setBookmark_existReminderOfSpecifiedBookmark_setReminder() {
        association.addReminder(ALICE, OPEN);
        Reminder reminder = new ReminderBuilder().withNote("Read").build();
        association.setReminder(OPEN, reminder);
        ReminderAssociation expectedAssociation = new ReminderAssociation();
        expectedAssociation.addReminder(ALICE, reminder);
        assertEquals(expectedAssociation, association);
    }

    @Test
    public void remove_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> association.deleteReminder(null));
    }

    @Test
    public void remove_notExistReminder_throwsBookmarkContainNoReminderException() {
        assertThrows(BookmarkContainNoReminderException.class, () -> association.deleteReminder(READ));
    }

    @Test
    public void remove_notExistReminderofSepcifiedBookmark_throwsReminderNotFoundException() {
        association.addReminder(ALICE, OPEN);
        Reminder reminder = new ReminderBuilder().withBookmark(ALICE).withNote("Read").build();
        assertThrows(ReminderNotFoundException.class, () -> association.deleteReminder(reminder));
    }

    @Test
    public void remove_existingReminder_removesReminder() {
        association.addReminder(ALICE, OPEN);
        association.deleteReminder(OPEN);
        ReminderAssociation expectedAssociation = new ReminderAssociation();
        assertEquals(expectedAssociation, association);
    }
}
