package seedu.mark.model.reminder;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.exceptions.BookmarkContainNoReminderException;
import seedu.mark.model.bookmark.exceptions.ExistReminderException;
import seedu.mark.model.bookmark.exceptions.ReminderNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class ReminderAssociation {
    //TODO: One bookmark may has multiple reminder in next version.
    HashMap<Bookmark, Reminder> association = new HashMap<>();

    public void addReminder(Bookmark bookmark, Reminder reminder) {
        requireAllNonNull(bookmark, reminder);
        if (association.containsKey(bookmark)) {
            throw new ExistReminderException();
        }
        association.put(bookmark, reminder);
    }

    public void deleteReminder(Reminder reminder) {
        requireAllNonNull(reminder);
        Bookmark bookmark = reminder.getBookmark();
        if (!association.containsKey(bookmark)) {
            throw new BookmarkContainNoReminderException();
        }
        if(!association.remove(bookmark, reminder)) {
            throw new ReminderNotFoundException();
        }
    }
}
