package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.TrainingManager;

/**
 * Represents a storage for {@link TrainingManager}.
 */
public interface AttendanceStorage {

    /**
     * Returns the file path of the Attendance data file.
     */
    Path getAthletickFilePath();

    Optional<TrainingManager> readTrainingManager() throws DataConversionException, IOException;

    Optional<TrainingManager> readTrainingManager(Path filePath) throws DataConversionException, IOException;

    void saveTrainingManager(TrainingManager trainingManager) throws IOException;

    void saveTrainingManager(TrainingManager trainingManager, Path filePath) throws IOException;
}
