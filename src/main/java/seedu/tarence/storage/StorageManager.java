package seedu.tarence.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.model.ReadOnlyStudentBook;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.UserPrefs;

/**
 * Manages storage of StudentBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private StudentBookStorage studentBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(StudentBookStorage studentBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.studentBookStorage = studentBookStorage;
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


    // ================ StudentBook methods ==============================

    @Override
    public Path getStudentBookFilePath() {
        return studentBookStorage.getStudentBookFilePath();
    }

    @Override
    public Optional<ReadOnlyStudentBook> readStudentBook() throws DataConversionException, IOException {
        return readStudentBook(studentBookStorage.getStudentBookFilePath());
    }

    @Override
    public Optional<ReadOnlyStudentBook> readStudentBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return studentBookStorage.readStudentBook(filePath);
    }

    @Override
    public void saveStudentBook(ReadOnlyStudentBook studentBook) throws IOException {
        saveStudentBook(studentBook, studentBookStorage.getStudentBookFilePath());
    }

    @Override
    public void saveStudentBook(ReadOnlyStudentBook studentBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        studentBookStorage.saveStudentBook(studentBook, filePath);
    }

}
