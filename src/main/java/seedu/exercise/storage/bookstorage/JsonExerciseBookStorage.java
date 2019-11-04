package seedu.exercise.storage.bookstorage;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.storage.serializablebook.JsonSerializableExerciseBook;

/**
 * A class to access ExerciseBook data stored as a json file on the hard disk.
 */
public class JsonExerciseBookStorage implements ResourceBookStorage<Exercise> {

    private static final Logger logger = LogsCenter.getLogger(JsonExerciseBookStorage.class);

    private Path filePath;

    public JsonExerciseBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getResourceBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyResourceBook<Exercise>> readResourceBook() throws DataConversionException {
        return readResourceBook(filePath);
    }

    @Override
    public Optional<ReadOnlyResourceBook<Exercise>> readResourceBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExerciseBook> jsonExerciseBook = JsonUtil.readJsonFile(
            filePath, JsonSerializableExerciseBook.class);
        if (!jsonExerciseBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExerciseBook.get().toModelType(Exercise.class, DEFAULT_EXERCISE_COMPARATOR));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveResourceBook(ReadOnlyResourceBook<Exercise> exerciseBook) throws IOException {
        saveResourceBook(exerciseBook, filePath);
    }

    @Override
    public void saveResourceBook(ReadOnlyResourceBook<Exercise> exerciseBook, Path filePath) throws IOException {
        requireNonNull(exerciseBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExerciseBook(exerciseBook), filePath);
    }

}
