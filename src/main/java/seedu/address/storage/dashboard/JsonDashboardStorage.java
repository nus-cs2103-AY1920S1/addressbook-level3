package seedu.address.storage.dashboard;

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
import seedu.address.model.dashboard.ReadOnlyDashboard;

/**
 * A class to access RecipeBook data stored as a json file on the hard disk.
 */
public class JsonDashboardStorage implements DashboardStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDashboardStorage.class);

    private Path filePath;

    public JsonDashboardStorage(Path filePath) {
        this.filePath = filePath;
    }


    public Path getDashboardFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDashboard> readDashboard() throws DataConversionException {
        return readDashboard(filePath);
    }


    /**
     * Similar to {@link #readDashboard()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDashboard> readDashboard(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDashboard> jsonDashboard = JsonUtil.readJsonFile(
                filePath, JsonSerializableDashboard.class);
        if (!jsonDashboard.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDashboard.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDashboard(ReadOnlyDashboard dashboard) throws IOException {
        saveDashboard(dashboard, filePath);
    }

    /**
     * Similar to {@link #saveDashboard(ReadOnlyDashboard)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDashboard(ReadOnlyDashboard dashboard, Path filePath) throws IOException {
        requireNonNull(dashboard);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDashboard(dashboard), filePath);
    }

}
