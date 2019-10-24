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
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;

/**
 * A class to access ModulePlanner data stored as a json file on the hard disk.
 */
public class JsonModulePlannerStorage implements ModulePlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModulePlannerStorage.class);

    private Path filePath;

    public JsonModulePlannerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModulePlannerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyModulePlanner> readModulePlanner(ModulesInfo modulesInfo)
            throws DataConversionException {
        return readModulePlanner(filePath, modulesInfo);
    }

    /**
     * Similar to {@link #readModulePlanner(ModulesInfo)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyModulePlanner> readModulePlanner(Path filePath, ModulesInfo modulesInfo)
            throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModulePlanner> jsonModulePlanner = JsonUtil.readJsonFile(
                filePath, JsonSerializableModulePlanner.class);
        if (!jsonModulePlanner.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonModulePlanner.get().toModelType(modulesInfo));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveModulePlanner(ReadOnlyModulePlanner modulePlanner) throws IOException {
        saveModulePlanner(modulePlanner, filePath);
    }

    /**
     * Similar to {@link #saveModulePlanner(ReadOnlyModulePlanner)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveModulePlanner(ReadOnlyModulePlanner modulePlanner, Path filePath) throws IOException {
        requireNonNull(modulePlanner);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModulePlanner(modulePlanner), filePath);
    }

}
