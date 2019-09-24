package seedu.algobase.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.exceptions.DataConversionException;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.commons.util.FileUtil;
import seedu.algobase.commons.util.JsonUtil;
import seedu.algobase.model.ReadOnlyAlgoBase;

/**
 * A class to access AlgoBase data stored as a json file on the hard disk.
 */
public class JsonAlgoBaseStorage implements AlgoBaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAlgoBaseStorage.class);

    private Path filePath;

    public JsonAlgoBaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAlgoBaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAlgoBase> readAlgoBase() throws DataConversionException {
        return readAlgoBase(filePath);
    }

    /**
     * Similar to {@link #readAlgoBase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAlgoBase> readAlgoBase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAlgoBase> jsonAlgoBase = JsonUtil.readJsonFile(
                filePath, JsonSerializableAlgoBase.class);
        if (!jsonAlgoBase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAlgoBase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAlgoBase(ReadOnlyAlgoBase algoBase) throws IOException {
        saveAlgoBase(algoBase, filePath);
    }

    /**
     * Similar to {@link #saveAlgoBase(ReadOnlyAlgoBase)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAlgoBase(ReadOnlyAlgoBase algoBase, Path filePath) throws IOException {
        requireNonNull(algoBase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAlgoBase(algoBase), filePath);
    }

}
