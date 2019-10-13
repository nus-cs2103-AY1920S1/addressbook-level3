package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.predicates.NameContainsKeywordsPredicate;
import seedu.mark.testutil.MarkBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Mark(), new Mark(modelManager.getMark()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMarkFilePath(Paths.get("bookmark/manager/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setMarkFilePath(Paths.get("new/bookmark/manager/file/path"));
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
    public void setMarkFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setMarkFilePath(null));
    }

    @Test
    public void setMarkFilePath_validPath_setsMarkFilePath() {
        Path path = Paths.get("bookmark/manager/file/path");
        modelManager.setMarkFilePath(path);
        assertEquals(path, modelManager.getMarkFilePath());
    }

    @Test
    public void hasBookmark_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBookmark(null));
    }

    @Test
    public void hasBookmark_bookmarkNotInMark_returnsFalse() {
        assertFalse(modelManager.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_bookmarkInMark_returnsTrue() {
        modelManager.addBookmark(ALICE);
        assertTrue(modelManager.hasBookmark(ALICE));
    }

    @Test
    public void hasFolder_folderNotInMark_returnsFalse() {
        assertFalse(modelManager.hasFolder(new Folder(VALID_FOLDER_CS2103T)));
    }

    @Test
    public void hasFolder_folderInMark_returnsTrue() {
        Folder validFolder = new Folder(VALID_FOLDER_CS2103T);
        modelManager.addFolder(validFolder, Folder.ROOT_FOLDER);
        assertTrue(modelManager.hasFolder(validFolder));
    }

    @Test
    public void getFilteredBookmarkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBookmarkList().remove(0));
    }

    @Test
    public void equals() {
        Mark mark = new MarkBuilder().withBookmark(ALICE).withBookmark(BENSON).build();
        Mark differentMark = new Mark();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(mark, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(mark, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different mark -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMark, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().value.split("\\s+");
        modelManager.updateFilteredBookmarkList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(mark, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMarkFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(mark, differentUserPrefs)));

        // different currentUrl, one non-null, one null -> return false
        modelManagerCopy.setCurrentUrl(ALICE.getUrl());
        assertFalse(modelManager.equals(modelManagerCopy));

        // different currentUrl, two non-null -> returns false
        modelManager.setCurrentUrl(BENSON.getUrl());
        assertFalse(modelManager.equals(modelManagerCopy));

        // same values (including currentUrl) -> return true
        modelManager.setCurrentUrl(ALICE.getUrl());
        assertTrue(modelManager.equals(modelManagerCopy));
    }
}
