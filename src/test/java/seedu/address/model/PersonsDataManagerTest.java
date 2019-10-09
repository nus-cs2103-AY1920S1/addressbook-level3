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
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class PersonsDataManagerTest {

    private PersonsDataManager personsDataManager = new PersonsDataManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), personsDataManager.getUserPrefs());
        assertEquals(new GuiSettings(), personsDataManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(personsDataManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personsDataManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        personsDataManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, personsDataManager.getUserPrefs());

        // Modifying userPrefs should not modify personsDataManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, personsDataManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personsDataManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        personsDataManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, personsDataManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personsDataManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        personsDataManager.setAddressBookFilePath(path);
        assertEquals(path, personsDataManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personsDataManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(personsDataManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        personsDataManager.addPerson(ALICE);
        assertTrue(personsDataManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> personsDataManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        personsDataManager = new PersonsDataManager(addressBook, userPrefs);
        PersonsDataManager personsDataManagerCopy = new PersonsDataManager(addressBook, userPrefs);
        assertTrue(personsDataManager.equals(personsDataManagerCopy));

        // same object -> returns true
        assertTrue(personsDataManager.equals(personsDataManager));

        // null -> returns false
        assertFalse(personsDataManager.equals(null));

        // different types -> returns false
        assertFalse(personsDataManager.equals(5));

        // different addressBook -> returns false
        assertFalse(personsDataManager.equals(new PersonsDataManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        personsDataManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(personsDataManager.equals(new PersonsDataManager(addressBook, userPrefs)));

        // resets personsDataManager to initial state for upcoming tests
        personsDataManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(personsDataManager.equals(new PersonsDataManager(addressBook, differentUserPrefs)));
    }
}
