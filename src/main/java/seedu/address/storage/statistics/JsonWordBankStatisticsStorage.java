package seedu.address.storage.statistics;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.statistics.WordBankStatistics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A class to access {@code WordBankStatistics} data stored as a json file on the hard disk.
 */
public class JsonWordBankStatisticsStorage implements WordBankStatisticsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWordBankStatisticsStorage.class);

    private Path filePath;

    public JsonWordBankStatisticsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getWordBankStatisticsFilePath() {
        return filePath;
    }

    @Override
    public Optional<WordBankStatistics> readWordBankStatistics() throws DataConversionException, IOException {
        return readWordBankStatistics(filePath);
    }

    /**
     * Similar to {@link #readWordBankStatistics()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<WordBankStatistics> readWordBankStatistics(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWordBankStatistics> jsonWordBankStats = JsonUtil.readJsonFile(
                filePath, JsonSerializableWordBankStatistics.class);

        if (jsonWordBankStats.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(jsonWordBankStats.get().toModelType());
    }

    @Override
    public void saveWordBankStatistics(WordBankStatistics statistics) throws IOException {
        saveWordBankStatistics(statistics, filePath);
    }

    /**
     * Similar to {@link #saveWordBankStatistics(WordBankStatistics)}.
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

    /**
     * Returns all the word bank statistics in the given directory.
     */
    public Optional<List<WordBankStatistics>> getWordBankStatisticsList() {
        List<WordBankStatistics> wordBankStatsList = new ArrayList<>();
        String pathString = "data/wbstats/";
        File dataDirectory = new File(pathString);
        String[] pathArray = dataDirectory.list();
        if (pathArray == null) {
            return Optional.empty();
        }

        for (String s : pathArray) {
            if (!s.endsWith(".json")) {
                continue;
            }
            String wordBankPathString = "data/wbstats/" + s;
            Path wordBankPath = Paths.get(wordBankPathString);
            try {
                Optional<WordBankStatistics> optionalWbStats = readWordBankStatistics(wordBankPath);
                String wbName = FileUtil.getJustFileName(wordBankPath.toString());
                WordBankStatistics wbStats = optionalWbStats
                        .orElse(WordBankStatistics.getEmpty(wbName));
                wordBankStatsList.add(wbStats);
            } catch (DataConversionException e) {
                e.printStackTrace();
            }
        }
        return Optional.of(wordBankStatsList);
    }
}
