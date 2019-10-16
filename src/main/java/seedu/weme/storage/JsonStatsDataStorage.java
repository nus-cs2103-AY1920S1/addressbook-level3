package seedu.weme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.FileUtil;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.statistics.StatsEngine;

/**
 * A class to access Weme statistics data stored as a json file on the hard disk.
 */
public class JsonStatsDataStorage implements StatsDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStatsDataStorage.class);

    private Path filePath;

    public JsonStatsDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getStatsDataPath() {
        return filePath;
    }

    @Override
    public Optional<StatsEngine> readStatsData() throws DataConversionException {
        return readStatsData(filePath);
    }

    /**
     * Similar to {@link #readStatsData(Path)}.
     *
     * @param statsDataPath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<StatsEngine> readStatsData(Path statsDataPath) throws DataConversionException {
        requireNonNull(statsDataPath);
        Optional<JsonSerializableStatsData> jsonStatsData =
                JsonUtil.readJsonFile(statsDataPath, JsonSerializableStatsData.class);
        if (!jsonStatsData.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonStatsData.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }


    public void saveStatsData(StatsEngine statsEngine) throws IOException {
        saveStatsData(statsEngine, filePath);
    }

    /**
     * Similar to {@link #saveStatsData(StatsEngine, Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStatsData(StatsEngine statsEngine, Path filePath) throws IOException {
        requireNonNull(statsEngine);
        requireNonNull(statsEngine);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStatsData(statsEngine), filePath);
    }

}
