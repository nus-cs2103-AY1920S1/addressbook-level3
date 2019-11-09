package seedu.mark.logic;

import java.nio.file.Path;
import java.util.concurrent.ScheduledExecutorService;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.reminder.Reminder;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Mark instance.
     *
     * @see seedu.mark.model.Model#getMark()
     */
    ReadOnlyMark getMark();

    /** Returns an unmodifiable view of the filtered list of bookmarks */
    ObservableList<Bookmark> getFilteredBookmarkList();

    /** Returns an unmodifiable view of the list of favorite bookmarks */
    ObservableList<Bookmark> getFavoriteBookmarkList();

    /** Returns an unmodifiable view of the folder structure */
    FolderStructure getFolderStructure();

    /**
     * Returns the user prefs' mark file path.
     */
    Path getMarkFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Wrapper for current url.
     * null if not present.
     *
     * @see seedu.mark.model.Model#getCurrentUrlProperty()
     */
    SimpleObjectProperty<Url> getCurrentUrlProperty();

    /**
     * Sets the current url.
     *
     * @see seedu.mark.model.Model#setCurrentUrl(Url)
     */
    void setCurrentUrl(Url url);


    /**
     * Returns a view of the annotated document.
     *
     * @see Model#getObservableDocument()
     */
    ObservableList<Paragraph> getObservableDocument();

    /**
     * Updates the view of document to the document given.
     * @param doc Document to update view and be shown.
     *
     * @see seedu.mark.model.Model#updateDocument(OfflineDocument)
     */
    void updateDocument(OfflineDocument doc);

    ObservableValue<String> getObservableOfflineDocNameCurrentlyShowing();

    void setOfflineDocNameCurrentlyShowing(String name);

    ObservableList<Reminder> getReminderList();

    /**
     * Returns an unmodifiable view of the autotag list.
     */
    ObservableList<SelectiveBookmarkTagger> getAutotags();

    SimpleObjectProperty<Bookmark> getBookmarkDisplayingCacheProperty();

    /**
     * Deletes expired reminders.
     */
    void startMarkTimer(ScheduledExecutorService executor);

    /**
     * Uses a reminder to find bookmark.
     *
     * @param reminder the reminder of the bookmark.
     * @return the bookmark found by the reminder.
     */
    Bookmark getBookmarkFromReminder(Reminder reminder);
}
