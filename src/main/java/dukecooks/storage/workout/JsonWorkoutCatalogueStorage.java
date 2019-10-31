package dukecooks.storage.workout;

import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.commons.util.FileUtil;
import dukecooks.commons.util.JsonUtil;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access Workout Catalgue data stored as a json file on the hard disk.
 */
public class JsonWorkoutCatalogueStorage implements WorkoutCatalogueStorage{

    private static final Logger logger = LogsCenter.getLogger(JsonWorkoutCatalogueStorage.class);

    private Path workoutFilePath;

    public JsonWorkoutCatalogueStorage(Path workoutFilePath) {
        this.workoutFilePath = workoutFilePath;
    }

    public Path getWorkoutFilePath() {
        return workoutFilePath;
    }

    @Override
    public Optional<ReadOnlyWorkoutCatalogue> readWorkoutCatalogue() throws DataConversionException, IOException {
        return readWorkoutCatalogue(workoutFilePath);
    }

    /**
     * Similar to {@link #readWorkoutCatalogue()}.
     *
     * @param workoutFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWorkoutCatalogue> readWorkoutCatalogue(Path workoutFilePath) throws DataConversionException,
            IOException {
        requireNonNull(workoutFilePath);

        Optional<JsonSerializableWorkoutCatalogue> jsonWorkoutCatalogue = JsonUtil
                .readJsonFile(workoutFilePath, JsonSerializableWorkoutCatalogue.class);
        if (!jsonWorkoutCatalogue.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWorkoutCatalogue.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + workoutFilePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue) throws IOException {
        saveWorkoutCatalogue(workoutCatalogue, workoutFilePath);
    }

    /**
     * Similar to {@link #saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue)}.
     *
     * @param filePath location of the data. Cannot be null.
     */

    @Override
    public void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue, Path filePath) throws IOException {
        requireNonNull(workoutCatalogue);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWorkoutCatalogue(workoutCatalogue), filePath);
    }
}
