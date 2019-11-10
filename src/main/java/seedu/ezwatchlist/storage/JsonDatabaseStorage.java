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
 * A class to access database of shows stored as a json file on the hard disk.
 */
public class JsonDatabaseStorage implements DatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWatchListStorage.class);

    private Path filePath;

    public JsonDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWatchList> readDatabase() throws DataConversionException {
        return readDatabase(filePath);
    }

    /**
     * Similar to {@link #readDatabase()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWatchList> readDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWatchList> jsonDatabase = JsonUtil.readJsonFile(
                filePath, JsonSerializableWatchList.class);
        if (!jsonDatabase.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDatabase.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDatabase(ReadOnlyWatchList database) throws IOException {
        saveDatabase(database, filePath);
    }

    /**
     * Similar to {@link #saveDatabase(ReadOnlyWatchList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDatabase(ReadOnlyWatchList database, Path filePath) throws IOException {
        requireNonNull(database);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWatchList(database), filePath);
    }

}

