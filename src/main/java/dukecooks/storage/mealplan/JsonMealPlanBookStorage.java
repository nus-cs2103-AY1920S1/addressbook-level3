package dukecooks.storage.mealplan;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.commons.util.FileUtil;
import dukecooks.commons.util.JsonUtil;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;

/**
 * A class to access MealPlanBook data stored as a json file on the hard disk.
 */
public class JsonMealPlanBookStorage implements MealPlanBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMealPlanBookStorage.class);

    private Path filePath;

    public JsonMealPlanBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMealPlansFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMealPlanBook> readMealPlanBook() throws DataConversionException {
        return readMealPlanBook(filePath);
    }

    /**
     * Similar to {@link #readMealPlanBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMealPlanBook> readMealPlanBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMealPlanBook> jsonMealPlanBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableMealPlanBook.class);
        if (!jsonMealPlanBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMealPlanBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMealPlanBook(ReadOnlyMealPlanBook mealPlanBook) throws IOException {
        saveMealPlanBook(mealPlanBook, filePath);
    }

    /**
     * Similar to {@link #saveMealPlanBook(ReadOnlyMealPlanBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMealPlanBook(ReadOnlyMealPlanBook mealPlanBook, Path filePath) throws IOException {
        requireNonNull(mealPlanBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMealPlanBook(mealPlanBook), filePath);
    }

}
