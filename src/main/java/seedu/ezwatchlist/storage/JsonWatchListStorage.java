package seedu.ezwatchlist.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.commons.exceptions.DataConversionException;
import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.commons.util.FileUtil;
import seedu.ezwatchlist.commons.util.JsonUtil;
import seedu.ezwatchlist.model.ReadOnlyWatchList;

/**
 * A class to access WatchList data stored as a json file on the hard disk.
 */
public class JsonWatchListStorage implements WatchListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWatchListStorage.class);

    private Path filePath;

    public JsonWatchListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWatchListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWatchList> readWatchList() throws DataConversionException {
        return readWatchList(filePath);
    }

    /**
     * Similar to {@link #readWatchList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWatchList> readWatchList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWatchList> jsonWatchList = JsonUtil.readJsonFile(
                filePath, JsonSerializableWatchList.class);
        if (!jsonWatchList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWatchList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWatchList(ReadOnlyWatchList watchList) throws IOException {
        saveWatchList(watchList, filePath);
    }

    /**
     * Similar to {@link #saveWatchList(ReadOnlyWatchList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWatchList(ReadOnlyWatchList watchList, Path filePath) throws IOException {
        requireNonNull(watchList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWatchList(watchList), filePath);
    }

}

