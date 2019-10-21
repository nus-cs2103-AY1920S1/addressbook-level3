package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;
import seedu.address.storage.wordbanks.WordBankListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends WordBankListStorage, UserPrefsStorage, WordBankStatisticsListStorage,
        GlobalStatisticsStorage {

    // ===================== word bank statistics =====================
    @Override
    Path getWordBankStatisticsListFilePath();

    @Override
    WordBankStatisticsList getWordBankStatisticsList();

    @Override
    void saveWordBankStatistics(WordBankStatistics statistics, Path filePath) throws IOException;


    // ===================== global statistics =====================
    @Override
    Path getGlobalStatisticsFilePath();

    @Override
    void saveGlobalStatistics(GlobalStatistics globalStatistics) throws IOException;


    // ===================== user prefs =====================
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;




    @Override
    void removeWordBank(String wordBankName);

    @Override
    Optional<ReadOnlyWordBank> getWordBank(Path wordBankPathFile) throws DataConversionException;

}
