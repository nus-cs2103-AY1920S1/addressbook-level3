package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CatalogueStorage catalogueStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CatalogueStorage catalogueStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.catalogueStorage = catalogueStorage;
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
    public Path getCatalogueFilePath() {
        return catalogueStorage.getCatalogueFilePath();
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException, IOException {
        return readCatalogue(catalogueStorage.getCatalogueFilePath());
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return catalogueStorage.readCatalogue(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyCatalogue addressBook) throws IOException {
        saveAddressBook(addressBook, catalogueStorage.getCatalogueFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyCatalogue addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        catalogueStorage.saveAddressBook(addressBook, filePath);
    }

}
