package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * API of the Storage component.
 * The Storage component is a container that contains all of the different storage for the different resources in
 * ExerHealth.
 */
public interface Storage extends UserPrefsStorage, PropertyBookStorage {

    // ================ UserPref methods ==============================
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;


    // ================ ExerciseBook methods ==============================
    Path getExerciseBookFilePath();

    Optional<ReadOnlyResourceBook<Exercise>> readExerciseBook() throws DataConversionException, IOException;

    Optional<ReadOnlyResourceBook<Exercise>> readExerciseBook(Path filePath)
        throws DataConversionException, IOException;

    void saveExerciseBook(ReadOnlyResourceBook<Exercise> exerciseBook) throws IOException;

    void saveExerciseBook(ReadOnlyResourceBook<Exercise> exerciseBook, Path filePath) throws IOException;

    Path getExerciseDatabaseFilePath();

    /**
     * Returns AllExerciseBook data as a {@link ReadOnlyResourceBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyResourceBook<Exercise>> readExerciseDatabase() throws DataConversionException, IOException;

    /**
     * @see #getExerciseDatabaseFilePath()
     */
    Optional<ReadOnlyResourceBook<Exercise>> readExerciseDatabase(Path filePath)
        throws DataConversionException, IOException;

    void saveExerciseDatabase(ReadOnlyResourceBook<Exercise> exerciseDatabase) throws IOException;

    // ================ RegimeBook methods ==============================
    Path getRegimeBookFilePath();

    Optional<ReadOnlyResourceBook<Regime>> readRegimeBook() throws DataConversionException, IOException;

    Optional<ReadOnlyResourceBook<Regime>> readRegimeBook(Path filePath)
        throws DataConversionException, IOException;

    void saveRegimeBook(ReadOnlyResourceBook<Regime> regimeBook) throws IOException;

    void saveRegimeBook(ReadOnlyResourceBook<Regime> exerciseBook, Path filePath) throws IOException;


    // ================ ScheduleBook methods ==============================
    Path getScheduleBookFilePath();

    Optional<ReadOnlyResourceBook<Schedule>> readScheduleBook() throws DataConversionException, IOException;

    Optional<ReadOnlyResourceBook<Schedule>> readScheduleBook(Path filePath)
        throws DataConversionException, IOException;

    void saveScheduleBook(ReadOnlyResourceBook<Schedule> scheduleBook) throws IOException;

    void saveScheduleBook(ReadOnlyResourceBook<Schedule> exerciseBook, Path filePath) throws IOException;


}
