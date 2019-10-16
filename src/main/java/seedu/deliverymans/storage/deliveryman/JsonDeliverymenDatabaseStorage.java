package seedu.deliverymans.storage.deliveryman;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;

/**
 * To be added.
 */
public class JsonDeliverymenDatabaseStorage implements DeliverymenDatabaseStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonDeliverymenDatabaseStorage.class);

    private Path filePath;

    /**
     * To be added.
     * @param filePath
     */
    public JsonDeliverymenDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDeliverymenDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase() throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase(Path filePath) throws DataConversionException,
            IOException {
        return Optional.empty();
    }

    @Override
    public void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase restaurantDatabase) throws IOException {

    }

    @Override
    public void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase, Path filePath) throws
            IOException {

    }
}
