package seedu.mark.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.commons.util.FileUtil;
import seedu.mark.commons.util.JsonUtil;
import seedu.mark.model.ReadOnlyMark;

/**
 * A class to access Mark data stored as a json file on the hard disk.
 */
public class JsonMarkStorage implements MarkStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMarkStorage.class);

    private Path filePath;

    public JsonMarkStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMarkFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMark> readMark() throws DataConversionException {
        return readMark(filePath);
    }

    /**
     * Similar to {@link #readMark()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMark> readMark(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMark> jsonMark = JsonUtil.readJsonFile(
                filePath, JsonSerializableMark.class);
        if (!jsonMark.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMark.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMark(ReadOnlyMark mark) throws IOException {
        saveMark(mark, filePath);
    }

    /**
     * Similar to {@link #saveMark(ReadOnlyMark)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMark(ReadOnlyMark mark, Path filePath) throws IOException {
        requireNonNull(mark);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMark(mark), filePath);
    }

}
