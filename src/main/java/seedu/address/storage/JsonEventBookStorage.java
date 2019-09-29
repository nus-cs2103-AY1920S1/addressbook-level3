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
import seedu.address.model.ReadOnlyEventBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonEventBookStorage() {}
    public JsonEventBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Path getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException {
        return readEventBook(filePath);
    }

    /**
     * Similar to {@link #readEventBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventBook> readEventBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventBook> jsonEventBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableEventBook.class);
        if (!jsonEventBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath);
    }

    /**
     * Similar to {@link #saveEventBook(ReadOnlyEventBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEventBook(ReadOnlyEventBook eventBook, Path filePath) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventBook(eventBook), filePath);
    }

}
