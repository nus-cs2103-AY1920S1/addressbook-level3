package seedu.module.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.module.commons.core.LogsCenter;
import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.ReadOnlyUserPrefs;
import seedu.module.model.UserPrefs;

/**
 * Manages storage of ModuleBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ModuleBookStorage moduleBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ModuleBookStorage moduleBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.moduleBookStorage = moduleBookStorage;
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


    // ================ ModuleBook methods ==============================

    @Override
    public Path getModuleBookFilePath() {
        return moduleBookStorage.getModuleBookFilePath();
    }

    @Override
    public ReadOnlyModuleBook readModuleBook() {
        return readModuleBook(moduleBookStorage.getModuleBookFilePath());
    }

    @Override
    public ReadOnlyModuleBook readModuleBook(Path filePath) {
        logger.fine("Attempting to read data from file: " + filePath);
        return moduleBookStorage.readModuleBook(filePath);
    }

    @Override
    public void saveModuleBook(ReadOnlyModuleBook moduleBook) throws IOException {
        saveModuleBook(moduleBook, moduleBookStorage.getModuleBookFilePath());
    }

    @Override
    public void saveModuleBook(ReadOnlyModuleBook moduleBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        moduleBookStorage.saveModuleBook(moduleBook, filePath);
    }

}
