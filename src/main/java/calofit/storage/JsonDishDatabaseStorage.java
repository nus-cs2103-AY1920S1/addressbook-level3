package calofit.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import calofit.commons.core.LogsCenter;
import calofit.commons.exceptions.DataConversionException;
import calofit.commons.exceptions.IllegalValueException;
import calofit.commons.util.FileUtil;
import calofit.commons.util.JsonUtil;
import calofit.model.dish.ReadOnlyDishDatabase;

/**
 * A class to access DishDatabase data stored as a json file on the hard disk.
 */
public class JsonDishDatabaseStorage implements DishDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDishDatabaseStorage.class);

    private Path filePath;

    public JsonDishDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDishDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDishDatabase> readDishDatabase() throws DataConversionException {
        return readDishDatabase(filePath);
    }

    /**
     * Similar to {@link #readDishDatabase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDishDatabase> readDishDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDishDatabase> jsonDishDatabase = JsonUtil.readJsonFile(
                filePath, JsonSerializableDishDatabase.class);
        if (!jsonDishDatabase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDishDatabase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDishDatabase(ReadOnlyDishDatabase dishDatabase) throws IOException {
        saveDishDatabase(dishDatabase, filePath);
    }

    /**
     * Similar to {@link #saveDishDatabase(ReadOnlyDishDatabase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDishDatabase(ReadOnlyDishDatabase dishDatabase, Path filePath) throws IOException {
        requireNonNull(dishDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDishDatabase(dishDatabase), filePath);
    }

}
