package seedu.exercise.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ReadOnlyExerciseBook;

/**
 * A class to access ExerciseBook data stored as a json file on the hard disk.
 */
public class JsonExerciseBookStorage implements ExerciseBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExerciseBookStorage.class);

    private Path filePath;

    public JsonExerciseBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExerciseBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExerciseBook> readExerciseBook() throws DataConversionException {
        return readExerciseBook(filePath);
    }

    /**
     * Similar to {@link #readExerciseBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExerciseBook> readExerciseBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExerciseBook> jsonExerciseBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableExerciseBook.class);
        if (!jsonExerciseBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExerciseBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExerciseBook(ReadOnlyExerciseBook exerciseBook) throws IOException {
        saveExerciseBook(exerciseBook, filePath);
    }

    /**
     * Similar to {@link #saveExerciseBook(ReadOnlyExerciseBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExerciseBook(ReadOnlyExerciseBook exerciseBook, Path filePath) throws IOException {
        requireNonNull(exerciseBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExerciseBook(exerciseBook), filePath);
    }

}
