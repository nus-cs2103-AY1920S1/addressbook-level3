package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.ReadOnlyExerciseBook;

/**
 * Represents a storage for {@link ExerciseBook}.
 */
public interface ExerciseBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExerciseBookFilePath();

    /**
     * Returns ExerciseBook data as a {@link ReadOnlyExerciseBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExerciseBook> readExerciseBook() throws DataConversionException, IOException;

    /**
     * @see #getExerciseBookFilePath()
     */
    Optional<ReadOnlyExerciseBook> readExerciseBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExerciseBook} to the storage.
     * @param exerciseBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExerciseBook(ReadOnlyExerciseBook exerciseBook) throws IOException;

    /**
     * @see #saveExerciseBook(ReadOnlyExerciseBook)
     */
    void saveExerciseBook(ReadOnlyExerciseBook exerciseBook, Path filePath) throws IOException;

}
