package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TrainingManager;

/**
 * A class to access Attendance data stored as a json file on the hard disk.
 */
public class JsonTrainingManagerStorage implements TrainingManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAthletickStorage.class);

    private Path path;

    public JsonTrainingManagerStorage(Path path) {
        this.path = path;
    }

    @Override
    public Path getAthletickFilePath() {
        return path;
    }

    @Override
    public Optional<TrainingManager> readTrainingManager() throws DataConversionException {
        return readTrainingManager(this.path);
    }

    @Override
    public Optional<TrainingManager> readTrainingManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonTrainingManager> jsonAttendance = JsonUtil.readJsonFile(filePath, JsonTrainingManager.class);
        if (!jsonAttendance.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAttendance.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTrainingManager(TrainingManager trainingManager) throws IOException {
        saveTrainingManager(trainingManager, this.path);
    }

    @Override
    public void saveTrainingManager(TrainingManager trainingManager, Path filePath) throws IOException {
        requireNonNull(trainingManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonTrainingManager(trainingManager), filePath);
    }
}
