package seedu.ichifund.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.commons.exceptions.DataConversionException;
import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.commons.util.FileUtil;
import seedu.ichifund.commons.util.JsonUtil;
import seedu.ichifund.model.ReadOnlyFundBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonFundBookStorage implements FundBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFundBookStorage.class);

    private Path filePath;

    public JsonFundBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFundBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFundBook> readFundBook() throws DataConversionException {
        return readFundBook(filePath);
    }

    /**
     * Similar to {@link #readFundBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFundBook> readFundBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFundBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableFundBook.class);
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
    public void saveFundBook(ReadOnlyFundBook addressBook) throws IOException {
        saveFundBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveFundBook(ReadOnlyFundBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFundBook(ReadOnlyFundBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFundBook(addressBook), filePath);
    }

}
