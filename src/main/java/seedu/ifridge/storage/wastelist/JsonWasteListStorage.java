package seedu.ifridge.storage.wastelist;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.FileUtil;
import seedu.ifridge.commons.util.JsonUtil;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * A storage for waste lists
 */
public class JsonWasteListStorage implements WasteListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWasteListStorage.class);

    private Path filePath;

    public JsonWasteListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWasteListFilePath() {
        return filePath;
    }

    @Override
    public Optional<TreeMap<WasteMonth, WasteList>> readWasteList() throws DataConversionException {
        return readWasteList(filePath);
    }

    /**
     * Similar to {@link #readWasteList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<TreeMap<WasteMonth, WasteList>> readWasteList(Path filePath) throws DataConversionException {

        requireNonNull(filePath);

        Optional<JsonSerializableWasteArchive> jsonWasteArchive = JsonUtil.readJsonFile(
                filePath, JsonSerializableWasteArchive.class);
        if (!jsonWasteArchive.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWasteArchive.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWasteList(TreeMap<WasteMonth, WasteList> wasteArchive) throws IOException {
        saveWasteList(wasteArchive, filePath);
    }

    /**
     * Similar to {@link #saveWasteList}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveWasteList(TreeMap<WasteMonth, WasteList> wasteArchive, Path filePath) throws IOException {
        requireNonNull(wasteArchive);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWasteArchive(wasteArchive), filePath);
    }



}
