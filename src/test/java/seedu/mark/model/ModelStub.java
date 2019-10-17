package seedu.mark.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.reminder.Reminder;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getMarkFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMarkFilePath(Path markFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBookmark(Bookmark bookmark) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMark(ReadOnlyMark newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyMark getMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBookmark(Bookmark bookmark) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteBookmark(Bookmark target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBookmarks(List<Bookmark> bookmarksToAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Bookmark> getFilteredBookmarkList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredBookmarkList(Predicate<Bookmark> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFolder(Folder folder, Folder parentFolder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasFolder(Folder folder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFolders(FolderStructure foldersToAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTagger(SelectiveBookmarkTagger tagger) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void applyAllTaggers() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public SimpleObjectProperty<Url> getCurrentUrlProperty() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Url getCurrentUrl() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCurrentUrl(Url url) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addReminder(Bookmark bookmark, Reminder reminder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeReminder(Reminder reminder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void editReminder(Reminder targetReminder, Reminder editedReminder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isBookmarkHasReminder(Bookmark bookmark) {
        return false;
    }

    @Override
    public ObservableList<Reminder> getReminders() {
        return null;
    }

    @Override
    public void updateDocument(OfflineDocument doc) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Paragraph> getObservableDocument() {
        throw new AssertionError("This method should not be called.");
    }

}
