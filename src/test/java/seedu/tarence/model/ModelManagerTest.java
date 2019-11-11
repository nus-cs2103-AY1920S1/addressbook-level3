package seedu.tarence.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalModules.CS1101S;
import static seedu.tarence.testutil.TypicalPersons.ALICE;
import static seedu.tarence.testutil.TypicalPersons.BENSON;
import static seedu.tarence.testutil.TypicalStudents.AMY;
import static seedu.tarence.testutil.TypicalTutorials.CS1101S_LAB04;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.model.person.NameContainsKeywordsPredicate;
import seedu.tarence.testutil.ApplicationBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Application(), new Application(modelManager.getApplication()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setApplicationFilePath(Paths.get("tarence/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setApplicationFilePath(Paths.get("new/tarence/book/file/path"));
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
    public void setApplicationFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setApplicationFilePath(null));
    }

    @Test
    public void setApplicationFilePath_validPath_setsApplicationFilePath() {
        Path path = Paths.get("tarence/book/file/path");
        modelManager.setApplicationFilePath(path);
        assertEquals(path, modelManager.getApplicationFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInApplication_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInApplication_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInApplication_returnsFalse() {
        assertFalse(modelManager.hasStudent(AMY));
    }

    @Test
    public void hasStudent_studentInApplication_returnsTrue() {
        modelManager.addStudent(AMY);
        assertTrue(modelManager.hasStudent(AMY));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInApplication_returnsFalse() {
        assertFalse(modelManager.hasModule(CS1101S));
    }

    @Test
    public void hasModule_moduleInApplication_returnsTrue() {
        modelManager.addModule(CS1101S);
        assertTrue(modelManager.hasModule(CS1101S));
    }

    @Test
    public void hasTutorial_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTutorial(null));
    }

    @Test
    public void hasTutorial_tutorialNotInApplication_returnsFalse() {
        assertFalse(modelManager.hasTutorial(CS1101S_LAB04));
    }

    @Test
    public void hasTutorial_tutorialInApplication_returnsTrue() {
        modelManager.addTutorial(CS1101S_LAB04);
        assertTrue(modelManager.hasTutorial(CS1101S_LAB04));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void getFilteredTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTutorialList().remove(0));
    }

    @Test
    public void equals() {
        // TODO: update application builder
        Application application = new ApplicationBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Application differentApplication = new Application();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(application, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(application, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different Application -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentApplication, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(application, userPrefs)));
        // TODO: Add test for updateFilteredModuleList when Find command is implemented
        // TODO: Add test for updateFilteredTutorialList when Find command is implemented

        // resets modelManager to initial state for upcoming tests
        // TODO: Add test for updateFilteredModuleList when Find command is implemented
        // TODO: Add test for updateFilteredTutorialList when Find command is implemented
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setApplicationFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(application, differentUserPrefs)));
    }
}
