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
import seedu.address.model.customer.Customer;

/**
 * A class to access Customer DataBook data stored as a json file on the hard disk.
 */
public class JsonCustomerBookStorage implements CustomerBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCustomerBookStorage.class);

    private Path filePath;

    public JsonCustomerBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCustomerBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDataBook<Customer>> readCustomerBook() throws DataConversionException {
        return readCustomerBook(filePath);
    }

    /**
     * Similar to {@link #readCustomerBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDataBook<Customer>> readCustomerBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCustomerBook> jsonCustomerBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableCustomerBook.class);
        if (!jsonCustomerBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCustomerBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCustomerBook(ReadOnlyDataBook<Customer> customerBook) throws IOException {
        saveCustomerBook(customerBook, filePath);
    }

    /**
     * Similar to {@link #saveCustomerBook(ReadOnlyDataBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCustomerBook(ReadOnlyDataBook<Customer> customerBook, Path filePath) throws IOException {
        requireNonNull(customerBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCustomerBook(customerBook), filePath);
    }

}
