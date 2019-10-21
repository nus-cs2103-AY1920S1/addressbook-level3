package seedu.address.storage.globalstatistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.statistics.JsonSerializableWordBankStatistics;
import seedu.address.storage.statistics.WordBankStatisticsStorage;

/**
 * A class to access {@code GlobalStatistics} data stored as a json file on the hard disk.
 */
public class JsonGlobalStatisticsStorage implements GlobalStatisticsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGlobalStatisticsStorage.class);

    private Path filePath;

    public JsonGlobalStatisticsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getGlobalStatisticsFilePath() {
        return filePath;
    }

    @Override
    public Optional<GlobalStatistics> readGlobalStatistics() throws DataConversionException, IOException {
        return readGlobalStatistics(filePath);
    }

    /**
     * Similar to {@link #readGlobalStatistics()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<GlobalStatistics> readGlobalStatistics(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGlobalStatistics> jsonGlobalStats = JsonUtil.readJsonFile(
                filePath, JsonSerializableGlobalStatistics.class);

        if (jsonGlobalStats.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(jsonGlobalStats.get().toModelType());
    }

    @Override
    public void saveGlobalStatistics(GlobalStatistics statistics) throws IOException {
        saveGlobalStatistics(statistics, filePath);
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
}
