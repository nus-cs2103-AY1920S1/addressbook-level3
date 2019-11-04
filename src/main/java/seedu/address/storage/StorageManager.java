package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private StudyBuddyProStorage studyBuddyProStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(StudyBuddyProStorage studyBuddyProStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.studyBuddyProStorage = studyBuddyProStorage;
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
    public Optional<ReadOnlyStudyBuddyPro> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(studyBuddyProStorage.getFlashcardFilePath(), studyBuddyProStorage.getNoteFilePath(),
                studyBuddyProStorage.getCheatSheetFilePath());
    }

    @Override
    public Optional<ReadOnlyStudyBuddyPro> readAddressBook(Path flashcardFilePath, Path noteFilePath,
                                                           Path cheatsheetFilePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from files: " + flashcardFilePath
                + ", " + noteFilePath + ", " + cheatsheetFilePath);
        return studyBuddyProStorage.readAddressBook(flashcardFilePath, noteFilePath, cheatsheetFilePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyStudyBuddyPro addressBook) throws IOException {
        saveAddressBook(addressBook, studyBuddyProStorage.getFlashcardFilePath(),
                getNoteFilePath(), getCheatSheetFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyStudyBuddyPro addressBook, Path flashcardFilePath, Path noteFilePath,
                                Path cheatsheetFilePath) throws IOException {
        logger.fine("Attempting to write to data files: " + flashcardFilePath
                + ", " + noteFilePath + ", " + cheatsheetFilePath);
        studyBuddyProStorage.saveAddressBook(addressBook, flashcardFilePath, noteFilePath, cheatsheetFilePath);
    }

    // ================ CheatSheet methods ==============================

    @Override
    public Path getCheatSheetFilePath() {
        return studyBuddyProStorage.getCheatSheetFilePath();
    }

    // ================ Flashcard methods ==============================
    @Override
    public Path getFlashcardFilePath() {
        return studyBuddyProStorage.getFlashcardFilePath();
    }

    // ================ Note methods ==============================
    @Override
    public Path getNoteFilePath() {
        return studyBuddyProStorage.getNoteFilePath();
    }

}
