package seedu.address.storage.statistics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;

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
}
