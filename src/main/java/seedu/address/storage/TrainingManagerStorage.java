package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.TrainingManager;

/**
 * Represents a storage for {@link TrainingManager}.
 */
public interface TrainingManagerStorage {

    /**
     * Returns the file path of the TrainingManager data file.
     */
    Path getAthletickFilePath();

    /**
     * Loads TrainingManger from a JSON file.
     */
    Optional<TrainingManager> readTrainingManager() throws DataConversionException, IOException;

    Optional<TrainingManager> readTrainingManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves TrainingManager to a JSON file.
     */
    void saveTrainingManager(TrainingManager trainingManager) throws IOException;

    void saveTrainingManager(TrainingManager trainingManager, Path filePath) throws IOException;
}
