package seedu.jarvis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.model.AddressModel.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalPersons.ALICE;
import static seedu.jarvis.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.model.person.NameContainsKeywordsPredicate;
import seedu.jarvis.testutil.AddressBookBuilder;

public class AddressModelManagerTest {

    private AddressModelManager addressModelManager = new AddressModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), addressModelManager.getUserPrefs());
        assertEquals(new GuiSettings(), addressModelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(addressModelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressModelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        addressModelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, addressModelManager.getUserPrefs());

        // Modifying userPrefs should not modify addressModelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, addressModelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressModelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        addressModelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, addressModelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressModelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        addressModelManager.setAddressBookFilePath(path);
        assertEquals(path, addressModelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressModelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressModelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressModelManager.addPerson(ALICE);
        assertTrue(addressModelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressModelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        addressModelManager = new AddressModelManager(addressBook, userPrefs);
        AddressModelManager addressModelManagerCopy = new AddressModelManager(addressBook, userPrefs);
        assertTrue(addressModelManager.equals(addressModelManagerCopy));

        // same object -> returns true
        assertTrue(addressModelManager.equals(addressModelManager));

        // null -> returns false
        assertFalse(addressModelManager.equals(null));

        // different types -> returns false
        assertFalse(addressModelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(addressModelManager.equals(new AddressModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        addressModelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(addressModelManager.equals(new AddressModelManager(addressBook, userPrefs)));

        // resets addressModelManager to initial state for upcoming tests
        addressModelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(addressModelManager.equals(new AddressModelManager(addressBook, differentUserPrefs)));
    }
}
