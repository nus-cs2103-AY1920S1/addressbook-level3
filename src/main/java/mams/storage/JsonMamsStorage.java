package mams.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import mams.commons.core.LogsCenter;
import mams.commons.exceptions.DataConversionException;
import mams.commons.exceptions.IllegalValueException;
import mams.commons.util.FileUtil;
import mams.commons.util.JsonUtil;
import mams.model.ReadOnlyMams;

/**
 * A class to access Mams data stored as a json file on the hard disk.
 */
public class JsonMamsStorage implements MamsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMamsStorage.class);

    private Path filePath;

    public JsonMamsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMamsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMams> readMams() throws DataConversionException {
        return readMams(filePath);
    }

    /**
     * Similar to {@link #readMams()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMams> readMams(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMams> jsonMams = JsonUtil.readJsonFile(
                filePath, JsonSerializableMams.class);
        if (!jsonMams.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMams.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMams(ReadOnlyMams mams) throws IOException {
        saveMams(mams, filePath);
    }

    /**
     * Similar to {@link #saveMams(ReadOnlyMams)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMams(ReadOnlyMams mams, Path filePath) throws IOException {
        requireNonNull(mams);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMams(mams), filePath);
    }

}
