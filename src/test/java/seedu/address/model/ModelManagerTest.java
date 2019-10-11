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
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
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
    public void addAndGetParticipant_validId_returnsParticipant() {
        try {
            modelManager = new ModelManager();
            modelManager.addParticipant(TypicalParticipants.A);
            Participant participant = modelManager.getParticipant(new Id(PrefixType.P, 1));
            assertTrue(participant.equals(TypicalParticipants.A));
        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void deleteParticipant_validId_returnsParticipant() {
        try {
            modelManager = new ModelManager();
            modelManager.addParticipant(TypicalParticipants.A);
            TypicalTeams.clearTeamA();
            modelManager.addTeam(TypicalTeams.A);
            Participant participant = modelManager.deleteParticipant(new Id(PrefixType.P, 1));
            assertTrue(participant.equals(TypicalParticipants.A));
        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void updateParticipant_validId_returnsTrue() {
        try {
            modelManager = new ModelManager();
            modelManager.addParticipant(TypicalParticipants.A);
            TypicalTeams.clearTeamA();
            modelManager.addTeam(TypicalTeams.A);
            modelManager.updateParticipant(new Id(PrefixType.P, 1),
                    TypicalParticipants.A_UPDATED);
            assertTrue(TypicalTeams.A.getParticipants().get(0)
                    .equals(TypicalParticipants.A_UPDATED));
        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void getTeamByParticipantId_validId_returnsTeam() {
        try {
            modelManager = new ModelManager();
            TypicalTeams.clearTeamA();
            modelManager.addTeam(TypicalTeams.A);
            assertTrue(TypicalTeams.A
                    .equals(modelManager.getTeamByParticipantId(new Id(PrefixType.P, 1))));
        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void getTeamByMentorId_validId_returnsMentor() {
        try {
            modelManager = new ModelManager();
            TypicalTeams.clearTeamA();
            modelManager.addTeam(TypicalTeams.A);
            assertTrue(TypicalTeams.A
                    .equals(modelManager.getTeamByMentorId(new Id(PrefixType.M, 3))));
        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void updateMentor_validMentor_updatesMentor() {
        try {
            modelManager = new ModelManager();
            TypicalTeams.clearTeamA();
            modelManager.addTeam(TypicalTeams.A);
            modelManager.addMentor(TypicalMentors.A);
            modelManager.updateMentor(new Id(PrefixType.M, 3),
                    TypicalMentors.A_UPDATED);
            assertTrue(modelManager.getTeamByMentorId(new Id(PrefixType.M, 3))
                    .equals(TypicalTeams.A));

        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void addParticipantToTeam_validParticipant_addsParticipant() {
        try {
            modelManager = new ModelManager();
            TypicalTeams.clearTeamA();
            modelManager.addTeam(TypicalTeams.A);
            modelManager.addParticipantToTeam(new Id(PrefixType.T, 1),
                    TypicalParticipants.B);
            assertTrue(modelManager.getTeam(new Id(PrefixType.T, 1))
                    .getParticipants().size() == 2);
        } catch (AlfredException e) {
            // do nothing
        }
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
