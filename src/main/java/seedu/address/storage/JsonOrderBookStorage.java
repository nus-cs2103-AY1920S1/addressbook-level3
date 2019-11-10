package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;

/**
 * A class to access Order DataBook data stored as a json file on the hard disk.
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
    public Optional<ReadOnlyDataBook<Order>> readOrderBook() throws DataConversionException {
        return readOrderBook(filePath);
    }

    /**
     * Similar to {@link #readOrderBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDataBook<Order>> readOrderBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableOrderBook> jsonOrderBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableOrderBook.class);
        if (!jsonOrderBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonOrderBook.get().toModelType());
        } catch (IllegalValueException | ParseException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveOrderBook(ReadOnlyDataBook<Order> orderBook) throws IOException {
        saveOrderBook(orderBook, filePath);
    }

    /**
     * Similar to {@link #saveOrderBook(ReadOnlyDataBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveOrderBook(ReadOnlyDataBook<Order> orderBook, Path filePath) throws IOException {
        requireNonNull(orderBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableOrderBook(orderBook), filePath);
    }

}
