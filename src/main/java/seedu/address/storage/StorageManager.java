package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CatalogStorage catalogStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CatalogStorage catalogStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.catalogStorage = catalogStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getCatalogFilePath() {
        return catalogStorage.getCatalogFilePath();
    }

    @Override
    public Optional<ReadOnlyCatalog> readCatalog() throws DataConversionException, IOException {
        return readCatalog(catalogStorage.getCatalogFilePath());
    }

    @Override
    public Optional<ReadOnlyCatalog> readCatalog(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return catalogStorage.readCatalog(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyCatalog addressBook) throws IOException {
        saveAddressBook(addressBook, catalogStorage.getCatalogFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyCatalog addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        catalogStorage.saveAddressBook(addressBook, filePath);
    }

}
