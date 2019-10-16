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
import seedu.address.model.ReadOnlyHealthRecords;

/**
 * A class to access HealthRecords data stored as a json file on the hard disk.
 */
public class JsonHealthRecordsStorage implements HealthRecordsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHealthRecordsStorage.class);

    private Path filePath;

    public JsonHealthRecordsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHealthRecordsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException {
        return readHealthRecords(filePath);
    }

    /**
     * Similar to {@link #readHealthRecords()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHealthRecords> readHealthRecords(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableHealthRecords> jsonHealthRecords = JsonUtil.readJsonFile(
                filePath, JsonSerializableHealthRecords.class);
        if (!jsonHealthRecords.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHealthRecords.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException {
        saveHealthRecords(healthRecords, filePath);
    }

    /**
     * Similar to {@link #saveHealthRecords(ReadOnlyHealthRecords)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords, Path filePath) throws IOException {
        requireNonNull(healthRecords);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHealthRecords(healthRecords), filePath);
    }

}
