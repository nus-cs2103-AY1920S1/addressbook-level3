//package seedu.guilttrip.model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
//import static seedu.guilttrip.testutil.Assert.assertThrows;
//import static seedu.guilttrip.testutil.TypicalEntries.ALICE;
//import static seedu.guilttrip.testutil.TypicalEntries.BENSON;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.commons.core.GuiSettings;
//import seedu.guilttrip.model.entry.predicates.entries.DescriptionContainsKeywordsPredicate;
//import seedu.guilttrip.testutil.AddressBookBuilder;
//
//public class ModelManagerTest {
//
//    private ModelManager modelManager = new ModelManager();
//
//    @Test
//    public void constructor() {
//        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
//        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
//        assertEquals(new GuiltTrip(), new GuiltTrip(modelManager.getAddressBook()));
//    }
//
//    @Test
//    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
//    }
//
//    @Test
//    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
//        UserPrefs userPrefs = new UserPrefs();
//        userPrefs.setAddressBookFilePath(Paths.get("guilttrip/book/file/path"));
//        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
//        modelManager.setUserPrefs(userPrefs);
//        assertEquals(userPrefs, modelManager.getUserPrefs());
//
//        // Modifying userPrefs should not modify modelManager's userPrefs
//        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
//        userPrefs.setAddressBookFilePath(Paths.get("new/guilttrip/book/file/path"));
//        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
//    }
//
//    @Test
//    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
//    }
//
//    @Test
//    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
//        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
//        modelManager.setGuiSettings(guiSettings);
//        assertEquals(guiSettings, modelManager.getGuiSettings());
//    }
//
//    @Test
//    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
//    }
//
//    @Test
//    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
//        Path path = Paths.get("guilttrip/book/file/path");
//        modelManager.setAddressBookFilePath(path);
//        assertEquals(path, modelManager.getAddressBookFilePath());
//    }
//
//    @Test
//    public void hasPerson_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
//    }
//
//    @Test
//    public void hasPerson_personNotInAddressBook_returnsFalse() {
//        assertFalse(modelManager.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personInAddressBook_returnsTrue() {
//        modelManager.addPerson(ALICE);
//        assertTrue(modelManager.hasPerson(ALICE));
//    }
//
//    @Test
//    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
//    }
//
//    @Test
//    public void equals() {
//        GuiltTrip addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
//        GuiltTrip differentAddressBook = new GuiltTrip();
//        UserPrefs userPrefs = new UserPrefs();
//
//        // same values -> returns true
//        modelManager = new ModelManager(addressBook, userPrefs);
//        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
//        assertTrue(modelManager.equals(modelManagerCopy));
//
//        // same object -> returns true
//        assertTrue(modelManager.equals(modelManager));
//
//        // null -> returns false
//        assertFalse(modelManager.equals(null));
//
//        // different types -> returns false
//        assertFalse(modelManager.equals(5));
//
//        // different addressBook -> returns false
//        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));
//
//        // different filteredList -> returns false
//        String[] keywords = ALICE.getName().fullDesc.split("\\s+");
//        modelManager.updateFilteredPersonList(new DescriptionContainsKeywordsPredicate(Arrays.asList(keywords)));
//        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
//
//        // resets modelManager to initial state for upcoming tests
//        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_ENTRIES);
//
//        // different userPrefs -> returns false
//        UserPrefs differentUserPrefs = new UserPrefs();
//        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
//        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
//    }
//}
