package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.exceptions.WordBankNotFoundException;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;
import seedu.address.storage.wordbanks.WordBankListStorage;


/**
 * API of the Storage component
 */
public interface Storage extends WordBankListStorage, UserPrefsStorage, WordBankStatisticsListStorage,
        GlobalStatisticsStorage, AppSettingsStorage {

    // ===================== word bank list ==========================

    @Override
    Optional<ReadOnlyWordBankList> getWordBankList();

    @Override
    ObservableList<WordBank> getFilteredWordBankList();

    @Override
    void createWordBank(String wordBankName);

    @Override
    void removeWordBank(String wordBankName);

    @Override
    void importWordBank(String wordBankName, Path filePath)
            throws DataConversionException, WordBankNotFoundException, IllegalValueException;

    @Override
    void exportWordBank(String wordBankName, Path filePath);

    @Override
    void updateWordBank(WordBank wordBank);

    Path getWordBanksFilePath();

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

}
