package seedu.deliverymans.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.commons.util.FileUtil;
import seedu.deliverymans.commons.util.JsonUtil;
import seedu.deliverymans.model.ReadOnlyOrderBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonOrderBookStorage implements OrderBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonOrderBookStorage.class);

    private Path filePath;

    public JsonOrderBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getOrderBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException {
        return readOrderBook(filePath);
    }

    /**
     * Similar to {@link #readOrderBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableOrderBook> jsonOrderBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableOrderBook.class);
        if (!jsonOrderBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonOrderBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException {
        saveOrderBook(orderBook, filePath);
    }

    /**
     * Similar to {@link #saveOrderBook(ReadOnlyOrderBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException {
        requireNonNull(orderBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableOrderBook(orderBook), filePath);
    }

}

