package seedu.moneygowhere.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.commons.util.FileUtil;
import seedu.moneygowhere.commons.util.JsonUtil;
import seedu.moneygowhere.model.ReadOnlySpendingBook;

/**
 * A class to access SpendingBook data stored as a json file on the hard disk.
 */
public class JsonSpendingBookStorage implements SpendingBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSpendingBookStorage.class);

    private Path filePath;

    public JsonSpendingBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSpendingBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySpendingBook> readSpendingBook() throws DataConversionException {
        return readSpendingBook(filePath);
    }

    /**
     * Similar to {@link #readSpendingBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySpendingBook> readSpendingBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSpendingBook> jsonSpendingBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableSpendingBook.class);
        if (!jsonSpendingBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSpendingBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSpendingBook(ReadOnlySpendingBook spendingBook) throws IOException {
        saveSpendingBook(spendingBook, filePath);
    }

    /**
     * Similar to {@link #saveSpendingBook(ReadOnlySpendingBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSpendingBook(ReadOnlySpendingBook spendingBook, Path filePath) throws IOException {
        requireNonNull(spendingBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSpendingBook(spendingBook), filePath);
    }

}
