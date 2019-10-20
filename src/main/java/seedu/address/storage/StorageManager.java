package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.statistics.WordBankStatisticsStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private WordBankListStorage wordBankListStorage;
    private UserPrefsStorage userPrefsStorage;
    private WordBankStatisticsStorage wbStatsStorage;

    public StorageManager(WordBankListStorage wordBankListStorage, UserPrefsStorage userPrefsStorage,
                          WordBankStatisticsStorage wbStatsStorage) {
        super();
        this.wordBankListStorage = wordBankListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.wbStatsStorage = wbStatsStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getWordBankListFilePath() {
        return wordBankListStorage.getWordBankListFilePath();
    }

    @Override
    public Optional<ReadOnlyWordBank> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(wordBankListStorage.getWordBankListFilePath());
    }

    @Override
    public Optional<ReadOnlyWordBank> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return wordBankListStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyWordBank addressBook) throws IOException {
        saveAddressBook(addressBook, wordBankListStorage.getWordBankListFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyWordBank addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wordBankListStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ WordBankStatistics methods ==============================

    @Override
    public Optional<WordBankStatistics> readWordBankStatistics() throws DataConversionException, IOException {
        return readWordBankStatistics(wbStatsStorage.getWordBankStatisticsFilePath());
    }

    @Override
    public Optional<WordBankStatistics> readWordBankStatistics(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return wbStatsStorage.readWordBankStatistics(filePath);
    }

    @Override
    public void saveWordBankStatistics(WordBankStatistics wbStats) throws IOException {
        saveWordBankStatistics(wbStats, wbStatsStorage.getWordBankStatisticsFilePath());
    }

    @Override
    public void saveWordBankStatistics(WordBankStatistics wbStats, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wbStatsStorage.saveWordBankStatistics(wbStats, filePath);
    }

    @Override
    public Path getWordBankStatisticsFilePath() {
        return wbStatsStorage.getWordBankStatisticsFilePath();
    }

    // ================ static methods ==============================
    /**
     * Get the path to the wordbank statistics given the path to wordbank
     * e.g. getWbStatsStoragePath("data/pokemon.json") == "data/wbstats/pokemon.json"
     * @param wbPath The path of the wordbank
     */
    public static Path getWbStatsStoragePath(Path wbPath) {
        return Path.of(wbPath.getParent().toString(), "wbstats", wbPath.getFileName().toString());
    }
}
