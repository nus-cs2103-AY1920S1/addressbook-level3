package mams.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static mams.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import mams.commons.core.GuiSettings;
import mams.model.student.NameContainsKeywordsPredicate;
import mams.testutil.MamsBuilder;
import mams.testutil.Assert;
import mams.testutil.TypicalPersons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Mams(), new Mams(modelManager.getMams()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMamsFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setMamsFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        Assertions.assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setMamsFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setMamsFilePath(null));
    }

    @Test
    public void setMamsFilePath_validPath_setsMamsFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setMamsFilePath(path);
        assertEquals(path, modelManager.getMamsFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInMams_returnsFalse() {
        assertFalse(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInMams_returnsTrue() {
        modelManager.addPerson(TypicalPersons.ALICE);
        assertTrue(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        Mams mams = new MamsBuilder().withPerson(TypicalPersons.ALICE).withPerson(TypicalPersons.BENSON).build();
        Mams differentMams = new Mams();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(mams, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(mams, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different mams -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMams, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = TypicalPersons.ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(mams, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMamsFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(mams, differentUserPrefs)));
    }
}
