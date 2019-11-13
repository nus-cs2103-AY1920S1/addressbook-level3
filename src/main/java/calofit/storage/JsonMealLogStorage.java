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
import calofit.model.meal.ReadOnlyMealLog;

/**
 * A class to access MealLog data stored as a json file on the hard disk.
 */
public class JsonMealLogStorage implements MealLogStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMealLogStorage.class);

    private Path filePath;

    public JsonMealLogStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMealLogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMealLog> readMealLog() throws DataConversionException {
        return readMealLog(filePath);
    }

    /**
     * Similar to {@link #readMealLog()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMealLog> readMealLog(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMealLog> jsonMealLog = JsonUtil.readJsonFile(
                filePath, JsonSerializableMealLog.class);
        if (!jsonMealLog.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMealLog.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMealLog(ReadOnlyMealLog mealLog) throws IOException {
        saveMealLog(mealLog, filePath);
    }

    /**
     * Similar to {@link #saveMealLog(ReadOnlyMealLog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMealLog(ReadOnlyMealLog mealLog, Path filePath) throws IOException {
        requireNonNull(mealLog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMealLog(mealLog), filePath);
    }
}
