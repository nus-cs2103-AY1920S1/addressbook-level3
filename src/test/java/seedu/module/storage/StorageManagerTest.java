// package seedu.module.storage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static seedu.module.testutil.TypicalPersons.getTypicalAddressBook;

// import java.nio.file.Path;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.io.TempDir;

// import seedu.module.commons.core.GuiSettings;
// import seedu.module.model.ModuleBook;
// import seedu.module.model.ReadOnlyModuleBook;
// import seedu.module.model.UserPrefs;

// public class StorageManagerTest {

//     @TempDir
//     public Path testFolder;

//     private StorageManager storageManager;

//     @BeforeEach
//     public void setUp() {
//         JsonModuleBookStorage addressBookStorage = new JsonModuleBookStorage(getTempFilePath("ab"));
//         JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
//         storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
//     }

//     private Path getTempFilePath(String fileName) {
//         return testFolder.resolve(fileName);
//     }

//     @Test
//     public void prefsReadSave() throws Exception {
//         /*
//          * Note: This is an integration test that verifies the StorageManager is properly wired to the
//          * {@link JsonUserPrefsStorage} class.
//          * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
//          */
//         UserPrefs original = new UserPrefs();
//         original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
//         storageManager.saveUserPrefs(original);
//         UserPrefs retrieved = storageManager.readUserPrefs().get();
//         assertEquals(original, retrieved);
//     }

//     @Test
//     public void addressBookReadSave() throws Exception {
//         /*
//          * Note: This is an integration test that verifies the StorageManager is properly wired to the
//          * {@link JsonAddressBookStorage} class.
//          * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
//          */
//         ModuleBook original = getTypicalAddressBook();
//         storageManager.saveAddressBook(original);
//         ReadOnlyModuleBook retrieved = storageManager.readAddressBook().get();
//         assertEquals(original, new ModuleBook(retrieved));
//     }

//     @Test
//     public void getAddressBookFilePath() {
//         assertNotNull(storageManager.getAddressBookFilePath());
//     }

// }
