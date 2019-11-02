package seedu.address.storage.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;

/**
 * A class to access a list of {@code WordBankStatistics} data stored as a json file on the hard disk.
 */
public class JsonWordBankStatisticsListStorage implements WordBankStatisticsListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWordBankStatisticsListStorage.class);

    private WordBankStatisticsList wbStatsList;
    private Path filePath;

    public JsonWordBankStatisticsListStorage(Path dataFilePath) {
        requireNonNull(dataFilePath);
        Path wbStatsFilePath = Paths.get(dataFilePath.toString(), "wbstats");
        this.filePath = wbStatsFilePath;

        File dataDirectory = wbStatsFilePath.toFile();
        String[] pathArray = dataDirectory.list();

        if (pathArray == null) {
            this.wbStatsList = new WordBankStatisticsList(Collections.emptyList());
            return;
        }

        List<WordBankStatistics> wordBankStatsList = new ArrayList<>();
        for (String path : pathArray) {
            if (!path.endsWith(".json")) {
                continue;
            }
            Path wbStatsPath = Path.of(wbStatsFilePath.toString(), path);
            Optional<WordBankStatistics> optionalWbStats = readWordBankStatistics(wbStatsPath);
            optionalWbStats.ifPresent(wordBankStatsList::add);
        }
        this.wbStatsList = new WordBankStatisticsList(wordBankStatsList);
    }

    /**
     * Reads a word bank statistics from the data.
     * @param path The path of the word bank statistics to be read.
     */
    private Optional<WordBankStatistics> readWordBankStatistics(Path path) {
        requireNonNull(path);

        try {
            Optional<JsonSerializableWordBankStatistics> jsonWbStats = JsonUtil.readJsonFile(
                    path, JsonSerializableWordBankStatistics.class);

            if (jsonWbStats.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(jsonWbStats.get().toModelType());

        } catch (DataConversionException e) {
            logger.info("Cannot convert to word bank statistics from  " + path + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Path getWordBankStatisticsListFilePath() {
        return filePath;
    }

    @Override
    public WordBankStatisticsList getWordBankStatisticsList() {
        return wbStatsList;
    }

    /**
     * Saves the word bank statistics.
     *
     * @param statistics statistics to be saved, cannot be null.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveWordBankStatistics(WordBankStatistics statistics, Path filePath)
            throws IOException {
        requireAllNonNull(statistics, filePath);
        File directory = new File(filePath.getParent().toUri());
        if (!directory.exists()) {
            boolean success = directory.mkdir();
            if (!success) {
                logger.fine("Cannot make directory for wbstats");
            }
        }
        JsonUtil.saveJsonFile(new JsonSerializableWordBankStatistics(statistics), filePath);
    }
}
