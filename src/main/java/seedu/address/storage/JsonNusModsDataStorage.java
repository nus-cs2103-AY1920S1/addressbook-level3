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
import seedu.address.model.NusModsData;

/**
 * A class to access NusModsData data stored as a JSON file on the hard disk.
 */
public class JsonNusModsDataStorage implements NusModsDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNusModsDataStorage.class);

    private Path filePath;

    public JsonNusModsDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getNusModsDataFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<NusModsData> readNusModsData() throws DataConversionException, IOException {
        return readNusModsData(filePath);
    }

    @Override
    public Optional<NusModsData> readNusModsData(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableNusModsData> jsonNusModsData = JsonUtil.readJsonFile(
                filePath, JsonSerializableNusModsData.class);
        if (!jsonNusModsData.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNusModsData.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }

    }

    @Override
    public void saveNusModsData(NusModsData nusModsData) throws IOException {
        saveNusModsData(nusModsData, filePath);
    }

    @Override
    public void saveNusModsData(NusModsData nusModsData, Path filePath) throws IOException {
        requireNonNull(nusModsData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNusModsData(nusModsData), filePath);

    }
}
