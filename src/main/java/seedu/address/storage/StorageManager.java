package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TimeBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private TimeBookStorage timeBookStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage, TimeBookStorage timeBookStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.timeBookStorage = timeBookStorage;
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

    // ================ TimeBook methods ==============================

    @Override
    public Path getTimeBookFilePath() {
        return timeBookStorage.getTimeBookFilePath();
    }

    @Override
    public Optional<TimeBook> readTimeBook() throws DataConversionException, IOException {
        return readTimeBook(timeBookStorage.getTimeBookFilePath());
    }

    @Override
    public Optional<TimeBook> readTimeBook(Path filePath) throws DataConversionException, IOException {
        return timeBookStorage.readTimeBook(filePath);
    }

    @Override
    public void saveTimeBook(TimeBook timeBook) throws IOException {
        saveTimeBook(timeBook, timeBookStorage.getTimeBookFilePath());
    }

    @Override
    public void saveTimeBook(TimeBook timeBook, Path filePath) throws IOException {
        timeBookStorage.saveTimeBook(timeBook, filePath);
    }


}
