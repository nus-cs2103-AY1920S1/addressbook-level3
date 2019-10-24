package seedu.module.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.module.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.module.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.module.commons.core.GuiSettings;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.testutil.ArchivedModuleBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ModuleBook(), new ModuleBook(modelManager.getModuleBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModuleBookFilePath(Paths.get("module/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setModuleBookFilePath(Paths.get("new/module/book/file/path"));
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
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModuleBookFilePath(null));
    }

    @Test
    public void setModuleBookFilePath_validPath_setsModuleBookFilePath() {
        Path path = Paths.get("module/book/file/path");
        modelManager.setModuleBookFilePath(path);
        assertEquals(path, modelManager.getModuleBookFilePath());
    }

    @Test
    public void hasModule_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModule(null));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void equals() {
        ModuleBook moduleBook = new ModuleBook();
        ModuleBook differentModuleBook = new ModuleBook();
        ArchivedModuleList archivedModules = new ArchivedModuleList();
        archivedModules.add(new ArchivedModuleBuilder().build());
        differentModuleBook.setArchivedModules(archivedModules);
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(moduleBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(moduleBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different ModuleBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentModuleBook, userPrefs)));

        // different filteredList -> returns false
        // String[] keywords = ALICE.getName().fullName.split("\\s+");
        // modelManager.updateFilteredModuleList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        // assertFalse(modelManager.equals(new ModelManager(moduleBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setModuleBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(moduleBook, differentUserPrefs)));
    }
}
