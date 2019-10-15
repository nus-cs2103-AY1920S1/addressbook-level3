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
import seedu.address.model.ReadOnlyProjectDashboard;

/**
 * A class to access ProjectDashboard data stored as a json file on the hard disk.
 */
public class JsonProjectDashboardStorage implements ProjectDashboardStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonProjectDashboardStorage.class);

    private Path filePath;

    public JsonProjectDashboardStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getProjectDashboardFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyProjectDashboard> readProjectDashBoard() throws DataConversionException {
        return readProjectDashBoard(filePath);
    }

    /**
     * Similar to {@link #readProjectDashBoard()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyProjectDashboard> readProjectDashBoard(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableProjectDashboard> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableProjectDashboard.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard) throws IOException {
        saveProjectDashboard(projectDashboard, filePath);
    }

    /**
     * Similar to {@link #saveProjectDashboard(ReadOnlyProjectDashboard)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard, Path filePath) throws IOException {
        requireNonNull(projectDashboard);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableProjectDashboard(projectDashboard), filePath);
    }

}
