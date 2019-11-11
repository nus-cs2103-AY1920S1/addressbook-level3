package seedu.system.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.system.testutil.Assert.assertThrows;
import static seedu.system.testutil.TypicalCompetitions.NUS_OPEN;
import static seedu.system.testutil.TypicalPersons.ALICE;
import static seedu.system.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.system.commons.core.GuiSettings;
import seedu.system.model.person.NameContainsKeywordsPredicate;
import seedu.system.model.util.SampleDataUtil;
import seedu.system.testutil.DataBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Data(), new Data(modelManager.getPersons()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPersonDataFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPersonDataFilePath(Paths.get("new/address/book/file/path"));
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
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefsFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setUserPrefsFilePath(path);
        assertEquals(path, modelManager.getUserPrefsFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        Data personData = new DataBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Data competitionData = new DataBuilder().withCompetition(NUS_OPEN).build();
        Data participationData =
            new Data(SampleDataUtil.getSampleParticipationData(personData, competitionData));
        Data differentPersonData = new Data();
        Data differentCompetitionData = new Data();
        Data differentParticipationData = new Data();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(personData, competitionData, participationData, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(personData, competitionData, participationData, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(
            modelManager.equals(
                new ModelManager(differentPersonData, competitionData, participationData, userPrefs)
            )
        );
        assertFalse(
            modelManager.equals(
                new ModelManager(personData, differentCompetitionData, participationData, userPrefs)
            )
        );
        assertFalse(
            modelManager.equals(
                new ModelManager(personData, competitionData, differentParticipationData, userPrefs)
            )
        );

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().toString().split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(
            modelManager.equals(
                new ModelManager(personData, differentCompetitionData, participationData, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPersonDataFilePath(Paths.get("differentFilePath"));
        assertFalse(
            modelManager.equals(
                new ModelManager(personData, competitionData, participationData, differentUserPrefs)
            )
        );
    }
}
