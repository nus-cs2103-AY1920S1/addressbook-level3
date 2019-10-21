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
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonStudyBuddyStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudyBuddyStorage.class);

    private Path cheatSheetFilePath;
    private Path flashcardFilePath;
    private Path noteFilePath;

    /**
     * New constructor to now take in 3 filepaths, one for each mode
     * @param flashcardFilePath
     * @param noteFilePath
     * @param cheatSheetFilePath
     */
    public JsonStudyBuddyStorage(Path flashcardFilePath, Path noteFilePath, Path cheatSheetFilePath) {
        this.cheatSheetFilePath = cheatSheetFilePath;
        this.flashcardFilePath = flashcardFilePath;
        this.noteFilePath = noteFilePath;
    }

    public Path getCheatSheetFilePath() {
        return cheatSheetFilePath;
    }

    public Path getFlashcardFilePath() {
        return flashcardFilePath;
    }

    public Path getNoteFilePath() {
        return noteFilePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException {
        return readAddressBook(flashcardFilePath, noteFilePath, cheatSheetFilePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param flashcardFilePath location of the data. Cannot be null.
     * @param noteFilePath location of the data. Cannot be null.
     * @param cheatSheetFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path flashcardFilePath, Path noteFilePath,
                                                         Path cheatSheetFilePath) throws DataConversionException {
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);

        Optional<JsonSerializableFlashcard> jsonFlashcard = JsonUtil.readJsonFile(
                flashcardFilePath, JsonSerializableFlashcard.class);
        Optional<JsonSerializableCheatSheet> jsonCheatSheet = JsonUtil.readJsonFile(
                cheatSheetFilePath, JsonSerializableCheatSheet.class);
        //Adjust for notes for top and bottom
        if (!jsonFlashcard.isPresent() && !jsonCheatSheet.isPresent()) {
            return Optional.empty();
        }
        AddressBook addressBook = new AddressBook();
        try {
            Optional.of(jsonFlashcard.get().toModelType(addressBook));
            Optional.of(jsonCheatSheet.get().toModelType(addressBook));
            return Optional.of(addressBook);
        } catch (IllegalValueException ive) {
            //Todo refactor code and create proper logger message
            //logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, flashcardFilePath, noteFilePath, cheatSheetFilePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param flashcardFilePath location of the data. Cannot be null.
     * @param noteFilePath location of the data. Cannot be null.
     * @param cheatSheetFilePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path flashcardFilePath,
                                Path noteFilePath, Path cheatSheetFilePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);

        FileUtil.createIfMissing(flashcardFilePath);
        FileUtil.createIfMissing(noteFilePath);
        FileUtil.createIfMissing(cheatSheetFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashcard(addressBook), flashcardFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableCheatSheet(addressBook), cheatSheetFilePath);
        //Add one more for note
    }
}
