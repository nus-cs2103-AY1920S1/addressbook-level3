package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Catalog;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.UserPrefs;
import seedu.address.storage.borrowerrecords.JsonBorrowerRecordsStorage;
import seedu.address.storage.catalog.JsonCatalogStorage;
import seedu.address.storage.loanrecords.JsonLoanRecordsStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCatalogStorage catalogStorage = new JsonCatalogStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonLoanRecordsStorage loanRecordsStorage = new JsonLoanRecordsStorage(getTempFilePath("lr"));
        JsonBorrowerRecordsStorage borrowerRecordsStorage =
                new JsonBorrowerRecordsStorage(getTempFilePath("br"));
        storageManager = new StorageManager(
                userPrefsStorage, loanRecordsStorage, catalogStorage, borrowerRecordsStorage
        );
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        Catalog original = getTypicalCatalog();
        storageManager.saveCatalog(original);
        ReadOnlyCatalog retrieved = storageManager.readCatalog().get();
        assertEquals(original, new Catalog(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getCatalogFilePath());
    }

}
