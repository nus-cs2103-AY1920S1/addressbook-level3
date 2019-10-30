package organice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import organice.commons.core.GuiSettings;
import organice.logic.commands.FindCommand;
import organice.logic.parser.ArgumentMultimap;
import organice.logic.parser.ArgumentTokenizer;
import organice.model.person.MatchedDonor;
import organice.model.person.MatchedPatient;
import organice.model.person.Person;
import organice.model.person.PersonContainsPrefixesPredicate;
import organice.testutil.AddressBookBuilder;

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
        Person person = null;
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(person));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(DOCTOR_ALICE);
        assertTrue(modelManager.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDoctor(null));
    }

    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDoctor(DOCTOR_ALICE.getNric()));
    }

    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        modelManager.addPerson(DOCTOR_ALICE);
        assertTrue(modelManager.hasDoctor(DOCTOR_ALICE.getNric()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void addMatchedDonor_success() {
        MatchedDonor matchedDonor = new MatchedDonor(DONOR_IRENE_DONOR);
        modelManager.addMatchedDonor(matchedDonor);

        List<Person> listOfMatches = modelManager.getMatchList();
        assertEquals(1, listOfMatches.size());
    }

    @Test
    public void addMatchedPatient_success() {
        MatchedPatient matchedPatient = new MatchedPatient(PATIENT_IRENE);
        modelManager.addMatchedPatient(matchedPatient);

        List<Person> listOfMatches = modelManager.getMatchList();
        assertEquals(1, listOfMatches.size());

    }

    @Test
    public void matchDonors() {
        modelManager.addPerson(PATIENT_IRENE);
        modelManager.addPerson(DONOR_IRENE_DONOR);

        MatchedDonor matchedDonor = new MatchedDonor(DONOR_IRENE_DONOR);

        modelManager.matchDonors(PATIENT_IRENE);
        List<Person> listOfMatches = modelManager.getMatchList();
        MatchedDonor matchedDonorAfterMatching = (MatchedDonor) listOfMatches.get(0);

        assertEquals(matchedDonor, matchedDonorAfterMatching);
    }

    @Test
    public void matchAllPatients() {
        modelManager.addPerson(PATIENT_IRENE);
        modelManager.addPerson(DONOR_IRENE_DONOR);

        MatchedPatient matchedPatient = new MatchedPatient(PATIENT_IRENE);

        modelManager.matchAllPatients();
        List<Person> listOfMatches = modelManager.getMatchList();
        MatchedPatient matchedPatientAfterMatching = (MatchedPatient) listOfMatches.get(0);

        assertEquals(matchedPatient, matchedPatientAfterMatching);
    }

    @Test
    public void removeMatches() {
        MatchedDonor matchedDonor = new MatchedDonor(DONOR_IRENE_DONOR);
        modelManager.addMatchedDonor(matchedDonor);

        modelManager.removeMatches();
        List<Person> listOfMatches = modelManager.getMatchList();
        assertEquals(0, listOfMatches.size());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(DOCTOR_ALICE).withPerson(PATIENT_IRENE).build();
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
        ArgumentMultimap searchParams = ArgumentTokenizer
                .tokenize(FindCommand.COMMAND_WORD + " n/" + DOCTOR_ALICE.getName().fullName, PREFIX_NAME);
        modelManager.updateFilteredPersonList(new PersonContainsPrefixesPredicate(searchParams));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
