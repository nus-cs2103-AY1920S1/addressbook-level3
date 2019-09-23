package seedu.address.storage.borrowerrecords;

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
import seedu.address.model.ReadOnlyBorrowerRecords;

/**
 * A class to access BorrowerRecords data stored as a json file on the hard disk.
 */
public class JsonBorrowerRecordsStorage implements BorrowerRecordsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBorrowerRecordsStorage.class);

    private Path filePath;

    public JsonBorrowerRecordsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBorrowerRecordsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBorrowerRecords> readBorrowerRecords() throws DataConversionException {
        return readBorrowerRecords(filePath);
    }

    /**
     * Similar to {@link #readBorrowerRecords()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBorrowerRecords> readBorrowerRecords(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBorrowerRecords> jsonBorrowerRecords = JsonUtil.readJsonFile(
                filePath, JsonSerializableBorrowerRecords.class);
        if (!jsonBorrowerRecords.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBorrowerRecords.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBorrowerRecords(ReadOnlyBorrowerRecords BorrowerRecords) throws IOException {
        saveBorrowerRecords(BorrowerRecords, filePath);
    }

    /**
     * Similar to {@link #saveBorrowerRecords(ReadOnlyBorrowerRecords)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBorrowerRecords(ReadOnlyBorrowerRecords borrowerRecords, Path filePath) throws IOException {
        requireNonNull(borrowerRecords);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBorrowerRecords(borrowerRecords), filePath);
    }

}
