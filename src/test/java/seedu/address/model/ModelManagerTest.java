package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudyPlans.SP_5;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.GuiTheme;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.storage.JsonModulesInfoStorage;
import seedu.address.storage.ModulesInfoStorage;

public class ModelManagerTest {

    private ModulesInfo modulesInfo;
    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        ModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(Paths.get("modules_cs.json"));
        modulesInfo = initModulesInfo(modulesInfoStorage);
        modelManager = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), modulesInfo);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ModulePlanner(), new ModulePlanner(modelManager.getModulePlanner(), modulesInfo));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModulePlannerFilePath(Paths.get("moduleplanner/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, GuiTheme.LIGHT));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setModulePlannerFilePath(Paths.get("new/moduleplanner/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4, GuiTheme.LIGHT);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setModulePlannerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModulePlannerFilePath(null));
    }

    @Test
    public void setModulePlannerFilePath_validPath_setsModulePlannerFilePath() {
        Path path = Paths.get("moduleplanner/file/path");
        modelManager.setModulePlannerFilePath(path);
        assertEquals(path, modelManager.getModulePlannerFilePath());
    }

    @Test
    public void hasStudyPlan_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudyPlan(null));
    }

    @Test
    public void hasStudyPlan_studyPlanNotInModulePlanner_returnsFalse() {
        assertFalse(modelManager.hasStudyPlan(SP_5));
    }

    @Test
    public void hasStudyPlan_studyPlanInModulePlanner_returnsTrue() {
        modelManager.addStudyPlan(SP_5);
        assertTrue(modelManager.hasStudyPlan(SP_5));
    }

    @Test
    public void getFilteredStudyPlanList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudyPlanList().remove(0));
    }

    @Test
    public void equals() {
        ModulePlanner modulePlanner = getTypicalModulePlanner();
        ModulePlanner differentModulePlanner = new ModulePlanner();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(modulePlanner, userPrefs, modulesInfo);
        ModelManager modelManagerCopy = new ModelManager(modulePlanner, userPrefs, modulesInfo);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different modulePlanner -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentModulePlanner, userPrefs, modulesInfo)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModulePlannerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(modulePlanner, differentUserPrefs, modulesInfo)));

    }

    /**
     * Initialises modules info from storage.
     */
    protected ModulesInfo initModulesInfo(ModulesInfoStorage storage) {
        ModulesInfo initializedModulesInfo;
        try {
            Optional<ModulesInfo> prefsOptional = storage.readModulesInfo();
            initializedModulesInfo = prefsOptional.orElse(new ModulesInfo());
        } catch (DataConversionException e) {
            initializedModulesInfo = new ModulesInfo();
        } catch (IOException e) {
            initializedModulesInfo = new ModulesInfo();
        }
        return initializedModulesInfo;
    }

}
