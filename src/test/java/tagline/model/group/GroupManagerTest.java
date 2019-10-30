//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalGroups.AVENGERS;
import static tagline.testutil.TypicalGroups.WAKANDAN_ROYAL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tagline.commons.core.GuiSettings;
import tagline.model.UserPrefs;
import tagline.testutil.GroupBookBuilder;

public class GroupManagerTest {

    private GroupManager groupManager = new GroupManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), groupManager.getUserPrefs());
        assertEquals(new GuiSettings(), groupManager.getGuiSettings());
        assertEquals(new GroupBook(), new GroupBook(groupManager.getGroupBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGroupBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        groupManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, groupManager.getUserPrefs());

        // Modifying userPrefs should not modify groupManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setGroupBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, groupManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        groupManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, groupManager.getGuiSettings());
    }

    @Test
    public void setGroupBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupManager.setGroupBookFilePath(null));
    }

    //TODO after implementing user prefs
    @Test
    public void setGroupBookFilePath_validPath_setsGroupBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        groupManager.setGroupBookFilePath(path);
        //assertEquals(path, groupManager.getGroupBookFilePath());
    }

    @Test
    public void hasGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupManager.hasGroup(null));
    }

    @Test
    public void hasGroup_personNotInGroupBook_returnsFalse() {
        assertFalse(groupManager.hasGroup(WAKANDAN_ROYAL));
    }

    @Test
    public void hasGroup_personInGroupBook_returnsTrue() {
        groupManager.addGroup(WAKANDAN_ROYAL);
        assertTrue(groupManager.hasGroup(WAKANDAN_ROYAL));
    }

    @Test
    public void getFilteredGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> groupManager.getFilteredGroupList().remove(0));
    }

    @Test
    public void equals() {
        GroupBook addressBook = new GroupBookBuilder().withGroup(WAKANDAN_ROYAL).withGroup(AVENGERS).build();
        GroupBook differentGroupBook = new GroupBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        groupManager = new GroupManager(addressBook, userPrefs);
        GroupManager groupManagerCopy = new GroupManager(addressBook, userPrefs);
        assertTrue(groupManager.equals(groupManagerCopy));

        // same object -> returns true
        assertTrue(groupManager.equals(groupManager));

        // null -> returns false
        assertFalse(groupManager.equals(null));

        // different types -> returns false
        assertFalse(groupManager.equals(5));

        // different addressBook -> returns false
        assertFalse(groupManager.equals(new GroupManager(differentGroupBook, userPrefs)));

        // different filteredList -> returns false
        // \\s+ splits on single or many whitespace
        groupManager = new GroupManager(addressBook, userPrefs);
        // using these strings as they are unique to WAKANDAN_ROYAL but not found in AVENGERS
        //String[] keywords = {"Manhattan", "York", "Loki", "Chitauri"};
        GroupName[] keywords = { WAKANDAN_ROYAL.getGroupName() };
        groupManager.updateFilteredGroupList(new GroupNameEqualsKeywordPredicate(Arrays.asList(keywords)));
        assertFalse(groupManager.equals(new GroupManager(addressBook, userPrefs)));

        // resets groupManager to initial state for upcoming tests
        groupManager.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        // different userPrefs -> returns false
        // TODO userPrefs feature not ready
        //UserPrefs differentUserPrefs = new UserPrefs();
        //differentUserPrefs.setGroupBookFilePath(Paths.get("differentFilePath"));
        //assertFalse(groupManager.equals(new GroupManager(addressBook, differentUserPrefs)));
    }
}
