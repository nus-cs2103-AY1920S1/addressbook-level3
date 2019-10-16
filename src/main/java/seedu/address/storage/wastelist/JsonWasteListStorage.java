package seedu.address.storage.wastelist;

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
import seedu.address.model.ReadOnlyWasteList;

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
    public Optional<ReadOnlyWasteList> readWasteList() throws DataConversionException {
        return readWasteList(filePath);
    }

    /**
     * Similar to {@link #readWasteList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWasteList> readWasteList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWasteList> jsonWasteList = JsonUtil.readJsonFile(
                filePath, JsonSerializableWasteList.class);
        if (!jsonWasteList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWasteList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWasteList(ReadOnlyWasteList wasteList) throws IOException {
        saveWasteList(wasteList, filePath);
    }

    /**
     * Similar to {@link #saveWasteList(ReadOnlyWasteList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveWasteList(ReadOnlyWasteList wasteList, Path filePath) throws IOException {
        requireNonNull(wasteList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWasteList(wasteList), filePath);
    }



}
