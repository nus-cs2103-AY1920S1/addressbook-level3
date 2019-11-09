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
import calofit.model.CalorieBudget;
import calofit.model.meal.ReadOnlyMealLog;

/**
 * A class to access {@link CalorieBudget} data stored as a json file on the hard disk.
 */
public class JsonCalorieBudgetStorage implements CalorieBudgetStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCalorieBudgetStorage.class);

    private Path filePath;

    public JsonCalorieBudgetStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCalorieBudgetFilePath() {
        return filePath;
    }

    @Override
    public Optional<CalorieBudget> readCalorieBudget() throws DataConversionException {
        return readCalorieBudget(filePath);
    }

    /**
     * Similar to {@link #readCalorieBudget()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<CalorieBudget> readCalorieBudget(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCalorieBudget> jsonBudget = JsonUtil.readJsonFile(
            filePath, JsonSerializableCalorieBudget.class);
        if (!jsonBudget.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBudget.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCalorieBudget(CalorieBudget budget) throws IOException {
        saveCalorieBudget(budget, filePath);
    }

    /**
     * Similar to {@link #saveCalorieBudget(ReadOnlyMealLog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveCalorieBudget(CalorieBudget budget, Path filePath) throws IOException {
        requireNonNull(budget);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCalorieBudget(budget), filePath);
    }
}
