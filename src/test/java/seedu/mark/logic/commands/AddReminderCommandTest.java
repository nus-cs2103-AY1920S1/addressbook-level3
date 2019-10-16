package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.ModelStub;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.StorageStub;

class AddReminderCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());

    private ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();

    @Test
    public void constructor_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddReminderCommand(null, new Note("open"), null));
    }

    @Test
    public void execute_bookmarkAcceptedByModel_addSuccessful() throws Exception {
        LocalDateTime time = LocalDateTime.now();
        Note note = new Note("Open");

        Bookmark validBookmark = model.getFilteredBookmarkList().get(0);
        Reminder validReminder = new Reminder(validBookmark, time, note);

        CommandResult commandResult = new AddReminderCommand(INDEX_FIRST_BOOKMARK, note, time)
                .execute(modelStub, new StorageStub());

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder),
                commandResult.getFeedbackToUser());
    }



    @Test
    public void execute_duplicateBookmark_throwsCommandException() throws CommandException {
        LocalDateTime time = LocalDateTime.now();
        Note note = new Note("Open");

        AddReminderCommand addReminderCommand = new AddReminderCommand(INDEX_FIRST_BOOKMARK, note, time);
        addReminderCommand.execute(modelStub, new StorageStub());

        assertThrows(CommandException.class, addReminderCommand.MESSAGE_DUPLICATE_REMINDER, () ->
                addReminderCommand.execute(modelStub, new StorageStub()));
    }

    /**
     * A Model stub that always accept the bookmark being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        public final HashMap<Bookmark, Reminder> association = new HashMap<>();
        public final ObservableList<Bookmark> bookmarks = model.getFilteredBookmarkList();

        @Override
        public void addReminder(Bookmark bookmark, Reminder reminder) {
            association.put(bookmark, reminder);
        }

        @Override
        public boolean isBookmarkHasReminder(Bookmark bookmark) {
            return association.containsKey(bookmark);
        }

        @Override
        public void saveMark() {
            // called by {@code AddReminderCommand#execute()}
        }

        @Override
        public ReadOnlyMark getMark() {
            return new Mark();
        }

        @Override
        public ObservableList<Bookmark> getFilteredBookmarkList() {
            return bookmarks;
        }
    }
}

