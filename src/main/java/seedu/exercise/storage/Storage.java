package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ExerciseBookStorage, RegimeBookStorage, UserPrefsStorage, PropertyManagerStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getExerciseBookFilePath();

    @Override
    Optional<ReadOnlyExerciseBook> readExerciseBook() throws DataConversionException, IOException;

    @Override
    void saveExerciseBook(ReadOnlyExerciseBook exerciseBook) throws IOException;

    @Override
    Path getRegimeBookFilePath();

    @Override
    Optional<ReadOnlyRegimeBook> readRegimeBook() throws DataConversionException, IOException;

    @Override
    void saveRegimeBook(ReadOnlyRegimeBook regimeBook) throws IOException;

    /**
     * Returns the file path of the data file.
     * @return
     */
    Path getAllExerciseBookFilePath();

    /**
     * Returns AllExerciseBook data as a {@link ReadOnlyExerciseBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any prolbem when reading from the storage.
     */
    Optional<ReadOnlyExerciseBook> readAllExerciseBook() throws DataConversionException, IOException;

    /**
     * @see #getAllExerciseBookFilePath()
     */
    Optional<ReadOnlyExerciseBook> readAllExerciseBook(Path filePath) throws DataConversionException, IOException;
}
