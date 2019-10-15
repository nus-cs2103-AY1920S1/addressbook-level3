package seedu.deliverymans.storage.restaurant;

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
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;

/**
 * A class to access RestaurantDatabase data stored as a json file on the hard disk.
 */
public class JsonRestaurantDatabaseStorage implements RestaurantDatabaseStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonRestaurantDatabaseStorage.class);

    private Path filePath;

    public JsonRestaurantDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRestaurantDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase() throws DataConversionException {
        return readRestaurantDatabase(filePath);
    }

    /**
     * Similar to {@link #readRestaurantDatabase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRestaurantDatabase> jsonRestaurantDatabase = JsonUtil.readJsonFile(
                filePath, JsonSerializableRestaurantDatabase.class);
        if (!jsonRestaurantDatabase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRestaurantDatabase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase) throws IOException {
        saveRestaurantDatabase(restaurantDatabase, filePath);
    }

    /**
     * Similar to {@link #saveRestaurantDatabase(ReadOnlyRestaurantDatabase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase, Path filePath) throws IOException {
        requireNonNull(restaurantDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRestaurantDatabase(restaurantDatabase), filePath);
    }

}
