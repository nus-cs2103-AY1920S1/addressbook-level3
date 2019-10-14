package seedu.flashcard.storage;

import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.commons.exceptions.DataConversionException;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.ReadOnlyUserPrefs;
import seedu.flashcard.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FlashcardStorage flashcardStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(FlashcardStorage flashcardStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.flashcardStorage = flashcardStorage;
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
    public Path getFlashcardListFilePath() {
        return flashcardStorage.getFlashcardListFilePath();
    }

    @Override
    public Optional<ReadOnlyFlashcardList> readFlashcardList() throws DataConversionException, IOException {
        return readFlashcardList(flashcardStorage.getFlashcardListFilePath());
    }

    @Override
    public Optional<ReadOnlyFlashcardList> readFlashcardList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return flashcardStorage.readFlashcardList(filePath);
    }

    @Override
    public void saveFlashcardList(ReadOnlyFlashcardList flashcardList) throws IOException {
        saveFlashcardList(flashcardList, flashcardStorage.getFlashcardListFilePath());
    }

    @Override
    public void saveFlashcardList(ReadOnlyFlashcardList flashcardList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        flashcardStorage.saveFlashcardList(flashcardList, filePath);
    }
}
