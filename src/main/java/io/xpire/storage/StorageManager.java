package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.ReadOnlyXpire;
import io.xpire.model.UserPrefs;

/**
 * Manages storage of Xpire data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XpireStorage xpireStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(XpireStorage xpireStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.xpireStorage = xpireStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return this.userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return this.userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        this.userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getXpireFilePath() {
        return this.xpireStorage.getXpireFilePath();
    }

    @Override
    public Optional<ReadOnlyXpire> readXpire() throws DataConversionException, IOException {
        return readXpire(this.xpireStorage.getXpireFilePath());
    }

    @Override
    public Optional<ReadOnlyXpire> readXpire(Path filePath) throws
            DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return this.xpireStorage.readXpire(filePath);
    }

    @Override
    public void saveXpire(ReadOnlyXpire xpire) throws IOException {
        saveXpire(xpire, this.xpireStorage.getXpireFilePath());
    }

    @Override
    public void saveXpire(ReadOnlyXpire xpire, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        this.xpireStorage.saveXpire(xpire, filePath);
    }

}
