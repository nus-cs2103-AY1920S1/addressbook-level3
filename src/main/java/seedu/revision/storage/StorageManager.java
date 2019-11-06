package seedu.revision.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.revision.commons.core.LogsCenter;
import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.ReadOnlyUserPrefs;
import seedu.revision.model.UserPrefs;

/**
 * Manages storage of RevisionTool data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RevisionToolStorage revisionToolStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(RevisionToolStorage revisionToolStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.revisionToolStorage = revisionToolStorage;
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


    // ================ RevisionTool methods ==============================

    @Override
    public Path getRevisionToolFilePath() {
        return revisionToolStorage.getRevisionToolFilePath();
    }

    @Override
    public Optional<ReadOnlyRevisionTool> readRevisionTool() throws DataConversionException, IOException {
        return readRevisionTool(revisionToolStorage.getRevisionToolFilePath());
    }

    @Override
    public Optional<ReadOnlyRevisionTool> readRevisionTool(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return revisionToolStorage.readRevisionTool(filePath);
    }

    @Override
    public void saveRevisionTool(ReadOnlyRevisionTool revisionTool) throws IOException {
        saveRevisionTool(revisionTool, revisionToolStorage.getRevisionToolFilePath());
    }

    @Override
    public void saveRevisionTool(ReadOnlyRevisionTool revisionTool, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        revisionToolStorage.saveRevisionTool(revisionTool, filePath);
    }

}
