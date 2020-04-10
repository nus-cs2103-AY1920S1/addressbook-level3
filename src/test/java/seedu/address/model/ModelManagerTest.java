package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AthletickBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Athletick(), new Athletick(modelManager.getAthletick()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAthletickFilePath(Paths.get("athletick/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAthletickFilePath(Paths.get("new/athletick/file/path"));
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
    public void setAthletickFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAthletickFilePath(null));
    }

    @Test
    public void setAthletickFilePath_validPath_setsAthletickFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAthletickFilePath(path);
        assertEquals(path, modelManager.getAthletickFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAthletick_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAthletick_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void setPerson_changePerson_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.setPerson(ALICE, BENSON);
        assertFalse(modelManager.hasPerson(ALICE));
        assertTrue(modelManager.hasPerson(BENSON));
    }

    @Test
    public void sortAthletickByName_modifyList_returnsTrue() {
        modelManager.addPerson(BENSON);
        modelManager.addPerson(ALICE);
        modelManager.sortAthletickByName();
        assertEquals(modelManager.getAthletick().getPersonList().get(0), ALICE);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        Athletick athletick = new AthletickBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Athletick differentAthletick = new Athletick();
        Performance performance = new Performance();
        TrainingManager trainingManager = new TrainingManager();
        UserPrefs userPrefs = new UserPrefs();
        HistoryManager history = new HistoryManager();

        // same values -> returns true
        modelManager = new ModelManager(athletick, performance, trainingManager, userPrefs, history);
        ModelManager modelManagerCopy = new ModelManager(athletick, performance, new TrainingManager(),
            userPrefs, new HistoryManager());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different Athletick -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAthletick, performance, trainingManager, userPrefs,
            history)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(athletick, performance, trainingManager,
                userPrefs, history)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAthletickFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(athletick, performance, trainingManager,
                differentUserPrefs, history)));

    }
}
