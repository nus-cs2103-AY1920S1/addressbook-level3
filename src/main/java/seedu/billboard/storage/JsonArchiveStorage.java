package seedu.billboard.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.commons.util.FileUtil;
import seedu.billboard.commons.util.JsonUtil;
import seedu.billboard.model.ReadOnlyBillboard;

/**
 * A class to access Archive data stored as a json file on the hard disk.
 */
public class JsonArchiveStorage extends JsonFileStorage implements ArchiveStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBillboardStorage.class);

    public JsonArchiveStorage(Path filePath) {
        super(filePath);
    }

    public Path getArchiveFilePath() {
        return getFilePath();
    }

    @Override
    public Optional<ReadOnlyBillboard> readArchive() throws DataConversionException {
        return readArchive(getFilePath());
    }

    /**
     * Similar to {@link #readArchive()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBillboard> readArchive(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBillboard> jsonArchive = JsonUtil.readJsonFile(
                filePath, JsonSerializableBillboard.class);
        if (jsonArchive.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonArchive.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveArchive(ReadOnlyBillboard archive) throws IOException {
        saveArchive(archive, getFilePath());
    }

    /**
     * Similar to {@link #saveArchive(ReadOnlyBillboard)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveArchive(ReadOnlyBillboard archive, Path filePath) throws IOException {
        requireNonNull(archive);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBillboard(archive), filePath);
    }

}
