package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static seedu.address.testutil.TypicalCards.getTypicalWordBank;

import java.nio.file.Path;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;

//import seedu.address.commons.core.GuiSettings;
//import seedu.address.model.wordbank.ReadOnlyWordBankList;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.wordbank.WordBank;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    //    @BeforeEach
    //    public void setUp() {
    //        JsonWordBankListStorage addressBookStorage = new JsonWordBankListStorage(getTempFilePath("ab"));
    //        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
    //        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    //    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }


    //    @Test
    //    public void prefsReadSave() throws Exception {
    //        /*
    //         * Note: This is an integration test that verifies the StorageManager is properly wired to the
    //         * {@link JsonUserPrefsStorage} class.
    //         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
    //         */
    //        UserPrefs original = new UserPrefs();
    //        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
    //        storageManager.saveUserPrefs(original);
    //        UserPrefs retrieved = storageManager.readUserPrefs().get();
    //        assertEquals(original, retrieved);
    //    }

    //    @Test
    //    public void addressBookReadSave() throws Exception {
    //        /*
    //         * Note: This is an integration test that verifies the StorageManager is properly wired to the
    //         * {@link JsonWordBankListStorage} class.
    //         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
    //         */
    //        WordBank original = getTypicalWordBank();
    //        storageManager.saveWordBanks(original);
    //        ReadOnlyWordBankList retrieved = storageManager.toModelType().get();
    //        assertEquals(original, new WordBank(retrieved));
    //    }

    //    @Test
    //    public void getWordBanksFilePath() {
    //        assertNotNull(storageManager.getWordBanksFilePath());
    //    }

}
