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

//Check if unused - Should be able to delete?
/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */

public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    //To delete
    public Path getNoteFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException {
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
    public Optional<ReadOnlyAddressBook> readAddressBook(Path flashcardFilePath, Path noteFilePath,
                                                         Path cheatSheetFilePath) throws DataConversionException {
        requireNonNull(flashcardFilePath);
        requireNonNull(noteFilePath);
        requireNonNull(cheatSheetFilePath);

        Optional<JsonSerializableFlashcard> jsonFlashcard = JsonUtil.readJsonFile(
                flashcardFilePath, JsonSerializableFlashcard.class);
        Optional<JsonSerializableFlashcard> jsonCheatSheet = JsonUtil.readJsonFile(
                flashcardFilePath, JsonSerializableFlashcard.class);
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
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
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
        JsonUtil.saveJsonFile(new JsonSerializableFlashcard(addressBook), cheatSheetFilePath);
        //Add one more for note
    }

    //============== flashcard tools
    @Override
    public Path getFlashcardFilePath() {
        return this.filePath;
    }

    //============== cheatsheet tools
    @Override
    public Path getCheatSheetFilePath() {
        return this.filePath;
    }

}
