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

/**
 * A class to access CentralManager data stored as a json file on the hard disk.
 * CentralManager contains all the managers needed such as Customer Manager, Driver Manager
 * and Task Manager.
 */
public class JsonCentralManagerStorage implements CentralManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCentralManagerStorage.class);

    private Path filePath;

    public JsonCentralManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getManagerFilePath() {
        return filePath;
    }

    public Optional<CentralManager> readManager() throws DataConversionException {
        return readManager(filePath);
    }

    /**
     * Returns a CentralManager which contains all the managers populated with data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    public Optional<CentralManager> readManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableCentralManager> jsonCentralManager = JsonUtil.readJsonFile(filePath,
                JsonSerializableCentralManager.class);

        //if unable to read file
        if (!jsonCentralManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCentralManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@link CentralManager} to the storage.
     *
     * @param centralManager Manager that contains all the managers needed.
     * @throws IOException if there was any problem writing to the file.
     */
    public void saveManager(CentralManager centralManager) throws IOException {
        saveManager(centralManager, filePath);
    }

    /**
     * Similar to #saveManager(CentralManager)
     *
     * @param centralManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void saveManager(CentralManager centralManager, Path filePath) throws IOException {
        requireNonNull(centralManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCentralManager(centralManager), filePath);
    }
}
