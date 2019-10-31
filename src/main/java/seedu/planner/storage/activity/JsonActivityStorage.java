package seedu.planner.storage.activity;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.commons.util.JsonUtil;
import seedu.planner.model.ReadOnlyActivity;

/**
 * A class to access Activity data stored as a json file on the hard disk.
 */
public class JsonActivityStorage implements ActivityStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonActivityStorage.class);

    private Path filePath;

    public JsonActivityStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getActivityFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyActivity> readActivity() throws DataConversionException {
        return readActivity(filePath);
    }

    /**
     * Similar to {@link #readActivity}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyActivity> readActivity(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableActivity> jsonActivity = JsonUtil.readJsonFile(
                filePath, JsonSerializableActivity.class);
        if (!jsonActivity.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonActivity.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveActivity(ReadOnlyActivity activity) throws IOException {
        saveActivity(activity, filePath);
    }

    /**
     * Similar to {@link #saveActivity(ReadOnlyActivity)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveActivity(ReadOnlyActivity activity, Path filePath) throws IOException {
        requireNonNull(activity);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableActivity(activity), filePath);
    }

}
