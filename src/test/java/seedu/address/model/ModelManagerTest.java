package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYPLANS;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.studyplan.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModulePlannerBuilder;

public class ModelManagerTest {

    //TODO implement tests

    /*
    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ModulePlanner(), new ModulePlanner(modelManager.getModulePlanner()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModulePlannerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setModulePlannerFilePath(Paths.get("new/address/book/file/path"));
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
    public void setModulePlannerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModulePlannerFilePath(null));
    }

    @Test
    public void setModulePlannerFilePath_validPath_setsModulePlannerFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setModulePlannerFilePath(path);
        assertEquals(path, modelManager.getModulePlannerFilePath());
    }

    @Test
    public void hasStudyPlan_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudyPlan(null));
    }

    @Test
    public void hasStudyPlan_studyPlanNotInModulePlanner_returnsFalse() {
        assertFalse(modelManager.hasStudyPlan(ALICE));
    }

    @Test
    public void hasStudyPlan_studyPlanInModulePlanner_returnsTrue() {
        modelManager.addStudyPlan(ALICE);
        assertTrue(modelManager.hasStudyPlan(ALICE));
    }

    @Test
    public void getFilteredStudyPlanList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudyPlanList().remove(0));
    }

    @Test
    public void equals() {
        ModulePlanner modulePlanner = new ModulePlannerBuilder().withStudyPlan(ALICE).withStudyPlan(BENSON).build();
        ModulePlanner differentModulePlanner = new ModulePlanner();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(modulePlanner, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(modulePlanner, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different modulePlanner -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentModulePlanner, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudyPlanList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(modulePlanner, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDYPLANS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModulePlannerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(modulePlanner, differentUserPrefs)));
    }
     */
}
