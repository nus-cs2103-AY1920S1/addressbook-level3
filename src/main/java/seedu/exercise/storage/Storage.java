package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ExerciseBookStorage, UserPrefsStorage {

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

}
