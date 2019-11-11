package seedu.address.storage.statistics;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;

/**
 * Represents a storage for list of {@link WordBankStatistics}.
 */
public interface WordBankStatisticsListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWordBankStatisticsListFilePath();

    WordBankStatisticsList getWordBankStatisticsList();

    /**
     * Saves the given {@link WordBankStatistics} to the storage.
     * @param statistics The statistics to be saved, cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBankStatistics(WordBankStatistics statistics, Path filePath) throws IOException;

    void removeWordBankStatistics(String name);
}
