package seedu.address.storage.exercise;

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
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;

/**
 * A class to access DukeCooks data stored as a json file on the hard disk.
 */
public class JsonWorkoutPlannerStorage implements WorkoutPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWorkoutPlannerStorage.class);

    private Path filePath;

    public JsonWorkoutPlannerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWorkoutPlannerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner() throws DataConversionException {
        return readWorkoutPlanner(filePath);
    }

    /**
     * Similar to {@link #readWorkoutPlanner()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExerciseCatalogue> jsonDukeCooks = JsonUtil.readJsonFile(
                filePath, JsonSerializableExerciseCatalogue.class);
        if (!jsonDukeCooks.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDukeCooks.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks) throws IOException {
        saveWorkoutPlanner(dukeCooks, filePath);
    }

    /**
     * Similar to {@link #saveWorkoutPlanner(ReadOnlyWorkoutPlanner)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks, Path filePath) throws IOException {
        requireNonNull(dukeCooks);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExerciseCatalogue(dukeCooks), filePath);
    }

}
