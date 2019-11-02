package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;
import seedu.address.storage.wordbanks.WordBankListStorage;

/**
 * Manages storage of Dukemon data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WordBankListStorage wordBankListStorage;
    private UserPrefsStorage userPrefsStorage;
    private WordBankStatisticsListStorage wbStatsStorage;
    private GlobalStatisticsStorage globalStatsStorage;
    private AppSettingsStorage settingsStorage;


    public StorageManager(WordBankListStorage wordBankListStorage,
                          UserPrefsStorage userPrefsStorage,
                          WordBankStatisticsListStorage wbStatsStorage,
                          GlobalStatisticsStorage globalStatsStorage,
                          AppSettingsStorage settingsStorage) {
        super();
        this.wordBankListStorage = wordBankListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.wbStatsStorage = wbStatsStorage;
        this.settingsStorage = settingsStorage;
        this.globalStatsStorage = globalStatsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ WordBankList methods ==============================

    @Override
    public Optional<ReadOnlyWordBankList> getWordBankList() {
        return wordBankListStorage.getWordBankList();
    }

    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return wordBankListStorage.getFilteredWordBankList();
    }

    @Override
    public void createWordBank(String wordBankName) {
        wordBankListStorage.createWordBank(wordBankName);
    }

    @Override
    public void removeWordBank(String wordBankName) {
        wordBankListStorage.removeWordBank(wordBankName);
    }

    @Override
    public void importWordBank(String wordBankName, Path filePath) {
        wordBankListStorage.importWordBank(wordBankName, filePath);
    }

    @Override
    public void exportWordBank(String wordBankName, Path filePath) {
        wordBankListStorage.exportWordBank(wordBankName, filePath);
    }

    @Override
    public void updateWordBank(WordBank wordBank) {
        wordBankListStorage.updateWordBank(wordBank);
    }


    public Path getWordBanksFilePath() {
        return wordBankListStorage.getWordBanksFilePath();
    }

    // ================ WordBankStatistics methods ==============================

    @Override
    public WordBankStatisticsList getWordBankStatisticsList() {
        return wbStatsStorage.getWordBankStatisticsList();
    }

    @Override
    public void saveWordBankStatistics(WordBankStatistics wbStats, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wbStatsStorage.saveWordBankStatistics(wbStats, filePath);
    }

    @Override
    public Path getWordBankStatisticsListFilePath() {
        return wbStatsStorage.getWordBankStatisticsListFilePath();
    }


    // ================ GlobalStatistics methods ==============================

    @Override
    public void saveGlobalStatistics(GlobalStatistics globalStatistics) throws IOException {
        saveGlobalStatistics(globalStatistics, globalStatsStorage.getGlobalStatisticsFilePath());
    }

    @Override
    public void saveGlobalStatistics(GlobalStatistics globalStatistics, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        globalStatsStorage.saveGlobalStatistics(globalStatistics, filePath);
    }

    @Override
    public Path getGlobalStatisticsFilePath() {
        return globalStatsStorage.getGlobalStatisticsFilePath();
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return globalStatsStorage.getGlobalStatistics();
    }


    // ================ static methods ==============================
    /**
     * Get the path to the wordbank statistics given the path to wordbank
     * e.g. getWbStatsStoragePath("data/pokemon.json") == "data/wbstats/pokemon.json"
     * @param wbPath The path of the wordbank
     */
    public static Path getWbStatsStoragePath(Path wbPath) {
        return Path.of(wbPath.toString(), "wbstats", wbPath.getFileName().toString());
    }

    //================ AppSettings methods ==============================
    @Override
    public Path getAppSettingsFilePath() {
        return settingsStorage.getAppSettingsFilePath();
    }

    @Override
    public Optional<AppSettings> readAppSettings() throws DataConversionException, IOException {
        return readAppSettings(settingsStorage.getAppSettingsFilePath());
    }

    @Override
    public Optional<AppSettings> readAppSettings(Path filePath) throws DataConversionException, IOException {
        return settingsStorage.readAppSettings(filePath);
    }

    @Override
    public void saveAppSettings(AppSettings settings) throws IOException {
        saveAppSettings(settings, settingsStorage.getAppSettingsFilePath());
    }

    @Override
    public void saveAppSettings(AppSettings settings, Path filePath) throws IOException {
        settingsStorage.saveAppSettings(settings, filePath);
    }


}
