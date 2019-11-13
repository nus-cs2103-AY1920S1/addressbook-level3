package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Classroom data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private NotebookStorage notebookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(NotebookStorage notebookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.notebookStorage = notebookStorage;
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


    // ================ Notebook methods ==============================

    @Override
    public Path getNotebookFilePath() {
        return notebookStorage.getNotebookFilePath();
    }

    @Override
    public Optional<ReadOnlyNotebook> readNotebook() throws DataConversionException, IOException {
        return readNotebook(notebookStorage.getNotebookFilePath());
    }

    @Override
    public Optional<ReadOnlyNotebook> readNotebook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return notebookStorage.readNotebook(filePath);
    }

    @Override
    public void saveNotebook(ReadOnlyNotebook notebook) throws IOException {
        saveNotebook(notebook, notebookStorage.getNotebookFilePath());
    }

    @Override
    public void saveNotebook(ReadOnlyNotebook notebook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        notebookStorage.saveNotebook(notebook, filePath);
    }

}
