package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.ReadOnlyStudyBuddyPro;

//Check if unused - Should be able to delete?
/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements StudyBuddyProStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStudyBuddyPro> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStudyBuddyPro> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param flashcardFilePath location of the data. Cannot be null.
     * @param cheatSheetFilePath location of the data. Cannot be null.
     * @param noteFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStudyBuddyPro> readAddressBook(Path flashcardFilePath, Path noteFilePath,
                                                           Path cheatSheetFilePath) throws DataConversionException {
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);

        Optional<JsonSerializableFlashcard> jsonFlashcard = JsonUtil.readJsonFile(
                flashcardFilePath, JsonSerializableFlashcard.class);
        Optional<JsonSerializableNote> jsonNote = JsonUtil.readJsonFile(
                noteFilePath, JsonSerializableNote.class);
        Optional<JsonSerializableCheatSheet> jsonCheatSheet = JsonUtil.readJsonFile(
                cheatSheetFilePath, JsonSerializableCheatSheet.class);

        if (!jsonFlashcard.isPresent() && !jsonNote.isPresent() && !jsonCheatSheet.isPresent()) {
            return Optional.empty();
        }
        StudyBuddyPro studyBuddyPro = new StudyBuddyPro();
        try {
            Optional.of(jsonFlashcard.get().toModelType(studyBuddyPro));
            Optional.of(jsonNote.get().toModelType(studyBuddyPro));
            Optional.of(jsonCheatSheet.get().toModelType(studyBuddyPro));
            return Optional.of(studyBuddyPro);
        } catch (IllegalValueException ive) {
            //Todo refactor code and create proper logger message
            //logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyStudyBuddyPro addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyStudyBuddyPro)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyStudyBuddyPro addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyStudyBuddyPro)}.
     *
     * @param flashcardFilePath location of the data. Cannot be null.
     * @param noteFilePath location of the data. Cannot be null.
     * @param cheatSheetFilePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyStudyBuddyPro addressBook, Path flashcardFilePath,
                                Path noteFilePath, Path cheatSheetFilePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);

        FileUtil.createIfMissing(flashcardFilePath);
        FileUtil.createIfMissing(noteFilePath);
        FileUtil.createIfMissing(cheatSheetFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashcard(addressBook), flashcardFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableNote(addressBook), noteFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashcard(addressBook), cheatSheetFilePath);
    }

    //============== flashcard tools
    @Override
    public Path getFlashcardFilePath() {
        return this.filePath;
    }

    //============== note tools
    @Override
    public Path getNoteFilePath() {
        return this.filePath;
    }

    //============== cheatsheet tools
    @Override
    public Path getCheatSheetFilePath() {
        return this.filePath;
    }

}
