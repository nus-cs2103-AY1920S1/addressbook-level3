package seedu.address.storage.globalstatistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.globalstatistics.GlobalStatistics;

/**
 * A class to access {@code GlobalStatistics} data stored as a json file on the hard disk.
 */
public class JsonGlobalStatisticsStorage implements GlobalStatisticsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGlobalStatisticsStorage.class);

    private GlobalStatistics globalStatistics;
    private Path dataFilePath;

    public JsonGlobalStatisticsStorage(Path filePath) {
        requireNonNull(filePath);


        this.dataFilePath = Path.of(filePath.toString(), "globalstats", "gstats.json");
        try {
            Optional<JsonSerializableGlobalStatistics> jsonGlobalStats = JsonUtil.readJsonFile(
                    dataFilePath, JsonSerializableGlobalStatistics.class);

            if (jsonGlobalStats.isEmpty()) {
                this.globalStatistics = new GlobalStatistics();
            } else {
                this.globalStatistics = jsonGlobalStats.get().toModelType();
            }
        } catch (DataConversionException e) {
            logger.info("Cannot convert file to global statistics " + e);
            this.globalStatistics = new GlobalStatistics();
        }
    }

    @Override
    public Path getGlobalStatisticsFilePath() {
        return dataFilePath;
    }

    @Override
    public void saveGlobalStatistics(GlobalStatistics statistics) throws IOException {
        saveGlobalStatistics(statistics, dataFilePath);
    }

    /**
     * Similar to {@link #saveGlobalStatistics(GlobalStatistics)}.
     *
     * @param statistics statistics to be saved, cannot be null.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveGlobalStatistics(GlobalStatistics statistics, Path filePath)
            throws IOException {
        requireAllNonNull(statistics, filePath);
        File directory = new File(filePath.getParent().toUri());
        if (!directory.exists()) {
            boolean success = directory.mkdir();
            if (!success) {
                logger.fine("Cannot make directory for globalstats");
            }
        }
        JsonUtil.saveJsonFile(new JsonSerializableGlobalStatistics(statistics), filePath);
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return globalStatistics;
    }
}
