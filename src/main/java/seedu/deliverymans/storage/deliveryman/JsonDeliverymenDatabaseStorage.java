package seedu.deliverymans.storage.deliveryman;

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
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;

/**
 * A class to access Deliverymen database data stored as a json file on the hard disk.
 */
public class JsonDeliverymenDatabaseStorage implements DeliverymenDatabaseStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonDeliverymenDatabaseStorage.class);

    private Path filePath;

    public JsonDeliverymenDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDeliverymenDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase() throws DataConversionException {
        return readDeliverymenDatabase(filePath);
    }

    /**
     * Similar to {@link #readDeliverymenDatabase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDeliverymenDatabase> jsonDeliverymenDatabase = JsonUtil.readJsonFile(
                filePath, JsonSerializableDeliverymenDatabase.class);
        if (!jsonDeliverymenDatabase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDeliverymenDatabase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase) throws IOException {
        saveDeliverymenDatabase(deliverymenDatabase, filePath);
    }

    @Override
    public void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase, Path filePath) throws
            IOException {
        requireNonNull(deliverymenDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDeliverymenDatabase(deliverymenDatabase), filePath);

    }
}
