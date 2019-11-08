package seedu.address.storage.quiz;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.quiz.ReadOnlyQuizBook;
import seedu.address.model.quiz.ReadOnlyUserPrefs;
import seedu.address.model.quiz.UserPrefs;



/**
 * Manages storage of AddressQuizBook data in local storage.
 */
public class StorageQuizManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageQuizManager.class);
    private QuizBookStorage quizBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageQuizManager(QuizBookStorage quizBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.quizBookStorage = quizBookStorage;
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
    public Path getAddressBookFilePath() {
        return quizBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyQuizBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(quizBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyQuizBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return quizBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyQuizBook addressBook) throws IOException {
        saveAddressBook(addressBook, quizBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyQuizBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        quizBookStorage.saveAddressBook(addressBook, filePath);
    }

}
