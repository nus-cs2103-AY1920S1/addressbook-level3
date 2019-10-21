package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyPurchaseHistory;
import seedu.savenus.model.ReadOnlyUserPrefs;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.SavingsStorage;
import seedu.savenus.model.sorter.CustomSorter;

/**
 * Manages storage of $aveNUS Menu Food data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MenuStorage menuStorage;
    private SavingsStorage savingsAccountStorage;
    private UserPrefsStorage userPrefsStorage;
    private RecsStorage userRecsStorage;
    private PurchaseHistoryStorage purchaseHistoryStorage;
    private CustomSortStorage customSortStorage;

    public StorageManager(MenuStorage menuStorage, UserPrefsStorage userPrefsStorage, RecsStorage userRecsStorage,
                          PurchaseHistoryStorage purchaseHistoryStorage,
                          CustomSortStorage customSortStorage, SavingsStorage savingsAccountStorage) {
        super();
        this.menuStorage = menuStorage;
        this.savingsAccountStorage = savingsAccountStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userRecsStorage = userRecsStorage;
        this.purchaseHistoryStorage = purchaseHistoryStorage;
        this.customSortStorage = customSortStorage;
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


    // ================ Menu methods ==============================

    @Override
    public Path getMenuFilePath() {
        return menuStorage.getMenuFilePath();
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException {
        return readMenu(menuStorage.getMenuFilePath());
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return menuStorage.readMenu(filePath);
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu) throws IOException {
        saveMenu(menu, menuStorage.getMenuFilePath());
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        menuStorage.saveMenu(menu, filePath);
    }

    // =============== Recommendation methods ========================
    @Override
    public Path getRecsFilePath() {
        return userRecsStorage.getRecsFilePath();
    }

    @Override
    public Optional<UserRecommendations> readRecs() throws DataConversionException, IOException {
        return readRecs(userRecsStorage.getRecsFilePath());
    }

    @Override
    public Optional<UserRecommendations> readRecs(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read recommendations data from file: " + filePath);
        return userRecsStorage.readRecs(filePath);
    }

    @Override
    public void saveRecs(UserRecommendations recs) throws IOException {
        saveRecs(recs, userRecsStorage.getRecsFilePath());
    }

    @Override
    public void saveRecs(UserRecommendations recs, Path filePath) throws IOException {
        logger.fine("Attempting to write recommendations to data file: " + filePath);
        userRecsStorage.saveRecs(recs, filePath);
    }

    // =============== PurchaseHistory methods ========================
    @Override
    public Path getPurchaseHistoryFilePath() {
        return purchaseHistoryStorage.getPurchaseHistoryFilePath();
    }

    @Override
    public Optional<ReadOnlyPurchaseHistory> readPurchaseHistory() throws DataConversionException, IOException {
        return readPurchaseHistory(purchaseHistoryStorage.getPurchaseHistoryFilePath());
    }

    @Override
    public Optional<ReadOnlyPurchaseHistory> readPurchaseHistory(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read purchase history data from file: " + filePath);
        return purchaseHistoryStorage.readPurchaseHistory(filePath);
    }

    @Override
    public void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) throws IOException {
        savePurchaseHistory(purchaseHistory, purchaseHistoryStorage.getPurchaseHistoryFilePath());
    }

    @Override
    public void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory, Path filePath) throws IOException {
        logger.fine("Attempting to write purchase history to data file: " + filePath);
        purchaseHistoryStorage.savePurchaseHistory(purchaseHistory, filePath);
    }

    // =============== CustomSorter methods ========================
    @Override
    public Path getSortFilePath() {
        return customSortStorage.getSortFilePath();
    }

    @Override
    public Optional<CustomSorter> readFields() throws DataConversionException, IOException {
        return readFields(customSortStorage.getSortFilePath());
    }

    @Override
    public Optional<CustomSorter> readFields(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read sort fields data from file: " + filePath);
        return customSortStorage.readFields(filePath);
    }

    @Override
    public void saveFields(CustomSorter sorter) throws IOException {
        saveFields(sorter, customSortStorage.getSortFilePath());
    }

    @Override
    public void saveFields(CustomSorter sorter, Path filePath) throws IOException {
        logger.fine("Attempting to write sort fields to data file: " + filePath);
        customSortStorage.saveFields(sorter, filePath);
    }


    @Override
    public Path getSavingsAccountFilePath() {
        return savingsAccountStorage.getSavingsAccountFilePath();
    }

    @Override
    public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount) throws IOException {
        saveSavingsAccount(savingsAccount, savingsAccountStorage.getSavingsAccountFilePath());
    }

    @Override
    public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        savingsAccountStorage.saveSavingsAccount(savingsAccount, filePath);
    }

    @Override
    public Optional<ReadOnlySavingsAccount> readSavingsAccount() throws DataConversionException, IOException {
        return readSavingsAccount(savingsAccountStorage.getSavingsAccountFilePath());
    }

    @Override
    public Optional<ReadOnlySavingsAccount> readSavingsAccount(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read savings account data from file: " + filePath);
        return savingsAccountStorage.readSavingsAccount(filePath);
    }
}
