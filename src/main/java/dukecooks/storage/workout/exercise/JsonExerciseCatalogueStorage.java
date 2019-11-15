package dukecooks.storage.workout.exercise;

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
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;

/**
 * A class to access DukeCooks data stored as a json file on the hard disk.
 */
public class JsonExerciseCatalogueStorage implements ExerciseCatalogueStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExerciseCatalogueStorage.class);

    private Path exerciseFilePath;

    public JsonExerciseCatalogueStorage(Path exerciseFilePath) {
        this.exerciseFilePath = exerciseFilePath;
    }

    public Path getExerciseFilePath() {
        return exerciseFilePath;
    }


    @Override
    public Optional<ReadOnlyExerciseCatalogue> readExerciseCatalogue() throws DataConversionException {
        return readExerciseCatalogue(exerciseFilePath);
    }

    /**
     * Similar to {@link #readExerciseCatalogue()}.
     *
     * @param exerciseFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExerciseCatalogue> readExerciseCatalogue(Path exerciseFilePath)
            throws DataConversionException {
        requireNonNull(exerciseFilePath);

        Optional<JsonSerializableExerciseCatalogue> jsonExerciseCatalogue = JsonUtil.readJsonFile(
                exerciseFilePath, JsonSerializableExerciseCatalogue.class);
        if (!jsonExerciseCatalogue.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExerciseCatalogue.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + exerciseFilePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExerciseCatalogue(ReadOnlyExerciseCatalogue exerciseCatalogue) throws IOException {
        saveExerciseCatalogue(exerciseCatalogue, exerciseFilePath);
    }

    /**
     * Similar to {@link #saveExerciseCatalogue(ReadOnlyExerciseCatalogue)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExerciseCatalogue(ReadOnlyExerciseCatalogue dukeCooks, Path filePath) throws IOException {
        requireNonNull(dukeCooks);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExerciseCatalogue(dukeCooks), filePath);
    }

}
