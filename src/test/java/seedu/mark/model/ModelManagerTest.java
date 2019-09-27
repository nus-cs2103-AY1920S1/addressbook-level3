package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.bookmark.NameContainsKeywordsPredicate;
import seedu.mark.testutil.BookmarkManagerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new BookmarkManager(), new BookmarkManager(modelManager.getBookmarkManager()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setBookmarkManagerFilePath(Paths.get("bookmark/manager/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setBookmarkManagerFilePath(Paths.get("new/bookmark/manager/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setBookmarkManagerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setBookmarkManagerFilePath(null));
    }

    @Test
    public void setBookmarkManagerFilePath_validPath_setsBookmarkManagerFilePath() {
        Path path = Paths.get("bookmark/manager/file/path");
        modelManager.setBookmarkManagerFilePath(path);
        assertEquals(path, modelManager.getBookmarkManagerFilePath());
    }

    @Test
    public void hasBookmark_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBookmark(null));
    }

    @Test
    public void hasBookmark_bookmarkNotInBookmarkManager_returnsFalse() {
        assertFalse(modelManager.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_bookmarkInBookmarkManager_returnsTrue() {
        modelManager.addBookmark(ALICE);
        assertTrue(modelManager.hasBookmark(ALICE));
    }

    @Test
    public void getFilteredBookmarkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBookmarkList().remove(0));
    }

    @Test
    public void equals() {
        BookmarkManager bookmarkManager = new BookmarkManagerBuilder().withBookmark(ALICE).withBookmark(BENSON).build();
        BookmarkManager differentBookmarkManager = new BookmarkManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(bookmarkManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(bookmarkManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different bookmarkManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentBookmarkManager, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredBookmarkList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(bookmarkManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBookmarkManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(bookmarkManager, differentUserPrefs)));
    }
}
