package seedu.deliverymans.storage.customer;

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
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;

/**
 * A class to access CustomerDatabase data stored as a json file on the hard disk.
 */
public class JsonCustomerDatabaseStorage implements CustomerDatabaseStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonCustomerDatabaseStorage.class);

    private Path filePath;

    public JsonCustomerDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCustomerDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCustomerDatabase> readCustomerDatabase() throws DataConversionException {
        return readCustomerDatabase(filePath);
    }

    /**
     * Similar to {@link #readCustomerDatabase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCustomerDatabase> readCustomerDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCustomerDatabase> jsonCustomerDatabase = JsonUtil.readJsonFile(
                filePath, JsonSerializableCustomerDatabase.class);
        if (!jsonCustomerDatabase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCustomerDatabase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase) throws IOException {
        saveCustomerDatabase(customerDatabase, filePath);
    }

    /**
     * Similar to {@link #saveCustomerDatabase(ReadOnlyCustomerDatabase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase, Path filePath) throws IOException {
        requireNonNull(customerDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCustomerDatabase(customerDatabase), filePath);
    }

}
