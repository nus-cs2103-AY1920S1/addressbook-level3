package seedu.mark.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;

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
}
