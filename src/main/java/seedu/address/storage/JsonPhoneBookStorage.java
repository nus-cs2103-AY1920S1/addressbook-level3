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
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.phone.Phone;

/**
 * A class to access Phone DataBook data stored as a json file on the hard disk.
 */
public class JsonPhoneBookStorage implements PhoneBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPhoneBookStorage.class);

    private Path filePath;

    public JsonPhoneBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPhoneBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDataBook<Phone>> readPhoneBook() throws DataConversionException {
        return readPhoneBook(filePath);
    }

    /**
     * Similar to {@link #readPhoneBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDataBook<Phone>> readPhoneBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePhoneBook> jsonPhoneBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePhoneBook.class);
        if (!jsonPhoneBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPhoneBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePhoneBook(ReadOnlyDataBook<Phone> phoneBook) throws IOException {
        savePhoneBook(phoneBook, filePath);
    }

    /**
     * Similar to {@link #savePhoneBook(ReadOnlyDataBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePhoneBook(ReadOnlyDataBook<Phone> phoneBook, Path filePath) throws IOException {
        requireNonNull(phoneBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePhoneBook(phoneBook), filePath);
    }

}
