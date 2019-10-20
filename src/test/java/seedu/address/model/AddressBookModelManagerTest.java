package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.AddressBookModel.PREDICATE_SHOW_ALL_PERSONS;
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
import seedu.main.model.MainModelManager;
import seedu.main.model.UserPrefs;

public class AddressBookModelManagerTest {

    private AddressBookModelManager addressBookModelManager = new AddressBookModelManager();
    private MainModelManager mainModelManager = new MainModelManager();

    @Test
    public void constructor() {
        assertEquals(new AddressBook(), new AddressBook(addressBookModelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        mainModelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, mainModelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, mainModelManager.getUserPrefs());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBookModelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        addressBookModelManager.setAddressBookFilePath(path);
        assertEquals(path, addressBookModelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBookModelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBookModelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBookModelManager.addPerson(ALICE);
        assertTrue(addressBookModelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> addressBookModelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        addressBookModelManager = new AddressBookModelManager(addressBook, userPrefs);
        AddressBookModelManager modelManagerCopy = new AddressBookModelManager(addressBook, userPrefs);
        assertTrue(addressBookModelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(addressBookModelManager.equals(addressBookModelManager));

        // null -> returns false
        assertFalse(addressBookModelManager.equals(null));

        // different types -> returns false
        assertFalse(addressBookModelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(addressBookModelManager.equals(new AddressBookModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        addressBookModelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(addressBookModelManager.equals(new AddressBookModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        addressBookModelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(addressBookModelManager.equals(new AddressBookModelManager(addressBook, differentUserPrefs)));
    }
}
