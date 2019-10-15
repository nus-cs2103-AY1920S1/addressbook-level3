package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.WorkoutPlannerUserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WorkoutPlannerStorage, UserPrefsStorage {

    @Override
    Optional<WorkoutPlannerUserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getDukeCooksFilePath();

    @Override
    Optional<ReadOnlyWorkoutPlanner> readDukeCooks() throws DataConversionException, IOException;

    @Override
    void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks) throws IOException;

}
