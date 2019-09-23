package seedu.address.storage.loanrecord;

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
import seedu.address.model.ReadOnlyLoanRecords;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonLoanRecordsStorage implements LoanRecordsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLoanRecordsStorage.class);

    private Path filePath;

    public JsonLoanRecordsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLoanRecordsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLoanRecords> readLoanRecords() throws DataConversionException {
        return readLoanRecords(filePath);
    }

    /**
     * Similar to {@link #readLoanRecords()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLoanRecords> readLoanRecords(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableLoanRecords> jsonLoanRecords = JsonUtil.readJsonFile(
                filePath, JsonSerializableLoanRecords.class);
        if (!jsonLoanRecords.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLoanRecords.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLoanRecords(ReadOnlyLoanRecords loanRecords) throws IOException {
        saveLoanRecords(loanRecords, filePath);
    }

    /**
     * Similar to {@link #saveLoanRecords(ReadOnlyLoanRecords)}
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLoanRecords(ReadOnlyLoanRecords loanRecords, Path filePath) throws IOException {
        requireNonNull(loanRecords);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLoanRecords(loanRecords), filePath);
    }

}
