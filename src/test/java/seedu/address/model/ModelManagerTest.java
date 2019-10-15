package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ORDER_SHIRTS;
import static seedu.address.testutil.TypicalTasks.PRINT_POSTERS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.task.NameContainsKeywordsPredicate;
import seedu.address.testutil.ProjectDashboardBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ProjectDashboard(), new ProjectDashboard(modelManager.getProjectDashboard()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setProjectDashboardFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setProjectDashboardFilePath(Paths.get("new/address/book/file/path"));
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
    public void setProjectDashboardFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setProjectDashboardFilePath(null));
    }

    @Test
    public void setProjectDashboardFilePath_validPath_setsProjectDashboardFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setProjectDashboardFilePath(path);
        assertEquals(path, modelManager.getProjectDashboardFilePath());
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInProjectDashboard_returnsFalse() {
        assertFalse(modelManager.hasTask(ORDER_SHIRTS));
    }

    @Test
    public void hasTask_taskInProjectDashboard_returnsTrue() {
        modelManager.addTask(ORDER_SHIRTS);
        assertTrue(modelManager.hasTask(ORDER_SHIRTS));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTasksList().remove(0));
    }

    @Test
    public void equals() {
        ProjectDashboard projectDashboard =
                new ProjectDashboardBuilder().withTask(ORDER_SHIRTS).withTask(PRINT_POSTERS).build();
        ProjectDashboard differentProjectDashboard = new ProjectDashboard();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(projectDashboard, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(projectDashboard, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different projectDashboard -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentProjectDashboard, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ORDER_SHIRTS.getName().fullName.split("\\s+");
        modelManager.updateFilteredTasksList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(projectDashboard, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setProjectDashboardFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(projectDashboard, differentUserPrefs)));
    }
}
