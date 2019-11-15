package seedu.revision.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.revision.commons.core.LogsCenter;
import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.commons.util.FileUtil;
import seedu.revision.commons.util.JsonUtil;
import seedu.revision.model.ReadOnlyRevisionTool;

/**
 * A class to access RevisionTool data stored as a json file on the hard disk.
 */
public class JsonRevisionToolStorage implements RevisionToolStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRevisionToolStorage.class);

    private Path filePath;

    public JsonRevisionToolStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRevisionToolFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRevisionTool> readRevisionTool() throws DataConversionException {
        return readRevisionTool(filePath);
    }

    /**
     * Similar to {@link #readRevisionTool()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRevisionTool> readRevisionTool(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRevisionTool> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableRevisionTool.class);
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
    public void saveRevisionTool(ReadOnlyRevisionTool revisionTool) throws IOException {
        saveRevisionTool(revisionTool, filePath);
    }

    /**
     * Similar to {@link #saveRevisionTool(ReadOnlyRevisionTool)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRevisionTool(ReadOnlyRevisionTool revisionTool, Path filePath) throws IOException {
        requireNonNull(revisionTool);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRevisionTool(revisionTool), filePath);
    }

}
