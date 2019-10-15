package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.BREAKFAST;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.ActivityBookBuilder;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new ActivityBook(), new ActivityBook(modelManager.getActivityBook()));
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
    public void findPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.findPerson(null));
    }

    @Test
    public void findPerson_personInAddressBook_returnsSingle() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        List<String> keywords = Arrays.asList("Pauline");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        ArrayList<Person> searchResult = modelManager.findPerson(predicate);
        ArrayList<Person> expectedSearchResult = new ArrayList<Person>(Arrays.asList(ALICE));
        assertEquals(searchResult, expectedSearchResult);
    }

    @Test
    public void findPerson_personNotInAddressBook_returnsEmpty() {
        modelManager.addPerson(BENSON);
        List<String> keywords = Arrays.asList("Pauline");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        ArrayList<Person> searchResult = modelManager.findPerson(predicate);
        ArrayList<Person> expectedSearchResult = new ArrayList<Person>();
        assertEquals(searchResult, expectedSearchResult);
    }

    @Test
    public void findPerson_multiplePeopleInAddressBook_returnsMultiple() {
        Person aliceFamilyMember = new PersonBuilder(ALICE).withName("Adam Pauline").build();
        modelManager.addPerson(ALICE);
        modelManager.addPerson(aliceFamilyMember);
        List<String> keywords = Arrays.asList("Pauline");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        ArrayList<Person> searchResult = modelManager.findPerson(predicate);
        ArrayList<Person> expectedSearchResult = new ArrayList<Person>(Arrays.asList(ALICE, aliceFamilyMember));
        assertEquals(searchResult, expectedSearchResult);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void setActivityBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setActivityBookFilePath(null));
    }

    @Test
    public void setActivityBookFilePath_validPath_setsActivityBookFilePath() {
        Path path = Paths.get("activity/book/file/path");
        modelManager.setActivityBookFilePath(path);
        assertEquals(path, modelManager.getActivityBookFilePath());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        ActivityBook activityBook = new ActivityBookBuilder().withActivity(BREAKFAST).build();
        ActivityBook differentActivityBook = new ActivityBook();
        UserPrefs userPrefs = new UserPrefs();
        InternalState state = new InternalState();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, state, activityBook);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, state, activityBook);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(
                new ModelManager(differentAddressBook, userPrefs, state, activityBook)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(
                new ModelManager(addressBook, userPrefs, state, activityBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(
                new ModelManager(addressBook, differentUserPrefs, state, activityBook)));
    }
}
