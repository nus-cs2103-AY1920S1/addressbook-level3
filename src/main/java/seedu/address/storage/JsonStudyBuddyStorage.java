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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCheatSheetBook;
import seedu.address.model.ReadOnlyFlashcardBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonStudyBuddyStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudyBuddyStorage.class);

    private Path filePath;
    private Path cheatSheetFilePath;
    private Path flashcardFilePath;

    public JsonStudyBuddyStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * New constructor to now take in 2 filepaths, one for each mode
     * @param filePath
     * @param cheatSheetFilePath
     */
    public JsonStudyBuddyStorage(Path filePath, Path cheatSheetFilePath) {
        this.filePath = filePath;
        this.cheatSheetFilePath = cheatSheetFilePath;
    }

    /**
     * New constructor to now take in 3 filepaths, one for each mode
     * @param filePath
     * @param cheatSheetFilePath
     * @param flashcardFilePath
     */
    public JsonStudyBuddyStorage(Path filePath, Path cheatSheetFilePath, Path flashcardFilePath) {
        this.filePath = filePath;
        this.cheatSheetFilePath = cheatSheetFilePath;
        this.flashcardFilePath = flashcardFilePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    public Path getCheatSheetFilePath() {
        return cheatSheetFilePath;
    }

    public Path getFlashcardFilePath() {
        return cheatSheetFilePath;
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
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath, Path cheatSheetFilePath)
            throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);
        requireNonNull(cheatSheetFilePath);

        FileUtil.createIfMissing(filePath);
        FileUtil.createIfMissing(cheatSheetFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), cheatSheetFilePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath, Path cheatSheetFilePath,
                                Path flashcardFilePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);
        requireNonNull(cheatSheetFilePath);
        requireNonNull(flashcardFilePath);

        FileUtil.createIfMissing(filePath);
        FileUtil.createIfMissing(cheatSheetFilePath);
        FileUtil.createIfMissing(flashcardFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), cheatSheetFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), flashcardFilePath);
    }

    @Override
    public Optional<ReadOnlyCheatSheetBook> readCheatSheetBook() throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public Optional<ReadOnlyCheatSheetBook> readCheatSheetBook(Path filePath)
            throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook) throws IOException {
        saveCheatSheetBook(cheatSheetBook, cheatSheetFilePath);
    }

    @Override
    public void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook, Path filePath) throws IOException {
        requireNonNull(cheatSheetBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        //JsonUtil.saveJsonFile(new JsonSerializableCheatSheetBook(cheatSheetBook), filePath);
    }

    @Override
    public Optional<ReadOnlyFlashcardBook> readFlashcardBook() throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public Optional<ReadOnlyFlashcardBook> readFlashcardBook(Path filePath)
            throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public void saveFlashcardBook(ReadOnlyFlashcardBook flashcardBook) throws IOException {
        saveFlashcardBook(flashcardBook, flashcardFilePath);
    }

    @Override
    public void saveFlashcardBook(ReadOnlyFlashcardBook flashcardBook, Path filePath) throws IOException {
        requireNonNull(flashcardBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        //JsonUtil.saveJsonFile(new JsonSerializableFlashcardBook(flashcardBook), filePath);
    }
}
