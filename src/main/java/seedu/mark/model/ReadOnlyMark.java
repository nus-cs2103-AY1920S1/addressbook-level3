package seedu.mark.model;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.AutotagController;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.model.reminder.ReminderAssociation;

/**
 * Unmodifiable view of a Mark.
 */
public interface ReadOnlyMark {

    /**
     * Returns an unmodifiable view of the bookmarks list.
     * This list will not contain any duplicate bookmarks.
     */
    ObservableList<Bookmark> getBookmarkList();

    FolderStructure getFolderStructure();

    ReminderAssociation getReminderAssociation();

    ObservableList<Reminder> getReminderList();

    AutotagController getAutotagController();

    ObservableList<SelectiveBookmarkTagger> getAutotags();

    ObservableList<Paragraph> getAnnotatedDocument();

    ObservableValue<String> getOfflineDocCurrentlyShowing();

}
