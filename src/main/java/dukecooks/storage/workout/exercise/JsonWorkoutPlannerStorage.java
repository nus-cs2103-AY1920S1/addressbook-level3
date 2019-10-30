package dukecooks.storage.workout.exercise;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
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
import dukecooks.model.workout.ReadOnlyWorkoutPlanner;

/**
 * A class to access DukeCooks data stored as a json file on the hard disk.
 */
public class JsonWorkoutPlannerStorage implements WorkoutPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWorkoutPlannerStorage.class);

    private Path exerciseFilePath;
    private Path workoutFilePath;

    public JsonWorkoutPlannerStorage(Path exerciseFilePath, Path workoutFilePath) {
        this.exerciseFilePath = exerciseFilePath;
        this.workoutFilePath = workoutFilePath;
    }

    public Path getExerciseFilePath() {
        return exerciseFilePath;
    }

    public Path getWorkoutFilePath() {
        return workoutFilePath;
    }

    @Override
    public Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner() throws DataConversionException {
        return readWorkoutPlanner(exerciseFilePath, workoutFilePath);
    }

    /**
     * Similar to {@link #readWorkoutPlanner()}.
     *
     * @param exerciseFilePath location of the data. Cannot be null.
     * @param workoutFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner(Path exerciseFilePath,
                                                               Path workoutFilePath) throws DataConversionException {
        requireAllNonNull(exerciseFilePath, workoutFilePath);

        Optional<JsonSerializableWorkoutPlanner> jsonWorkoutPlanner = JsonUtil.readJsonFile(
                exerciseFilePath, JsonSerializableWorkoutPlanner.class);
        if (!jsonWorkoutPlanner.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWorkoutPlanner.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + exerciseFilePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks) throws IOException {
        saveWorkoutPlanner(dukeCooks, exerciseFilePath);
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
        JsonUtil.saveJsonFile(new JsonSerializableWorkoutPlanner(dukeCooks), filePath);
    }

}
