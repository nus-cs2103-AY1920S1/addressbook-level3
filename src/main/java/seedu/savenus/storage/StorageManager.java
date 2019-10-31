package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.model.purchase.ReadOnlyPurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.ReadOnlyUserPrefs;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.storage.alias.AliasStorage;
import seedu.savenus.storage.menu.MenuStorage;
import seedu.savenus.storage.purchase.PurchaseHistoryStorage;
import seedu.savenus.storage.recommend.RecsStorage;
import seedu.savenus.storage.savings.SavingsStorage;
import seedu.savenus.storage.sort.CustomSortStorage;
import seedu.savenus.storage.userprefs.UserPrefsStorage;
import seedu.savenus.storage.wallet.WalletStorage;

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
    private WalletStorage walletStorage;
    private CustomSortStorage customSortStorage;
    private AliasStorage aliasStorage;

    public StorageManager(MenuStorage menuStorage, UserPrefsStorage userPrefsStorage, RecsStorage userRecsStorage,
                          PurchaseHistoryStorage purchaseHistoryStorage, WalletStorage walletStorage,
                          CustomSortStorage customSortStorage, SavingsStorage savingsAccountStorage,
                          AliasStorage aliasStorage) {
        super();
        this.menuStorage = menuStorage;
        this.savingsAccountStorage = savingsAccountStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userRecsStorage = userRecsStorage;
        this.purchaseHistoryStorage = purchaseHistoryStorage;
        this.walletStorage = walletStorage;
        this.customSortStorage = customSortStorage;
        this.aliasStorage = aliasStorage;
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

    // =============== Wallet methods ========================
    @Override
    public Path getWalletFilePath() {
        return walletStorage.getWalletFilePath();
    }

    @Override
    public Optional<Wallet> readWallet() throws DataConversionException, IOException {
        return readWallet(walletStorage.getWalletFilePath());
    }

    @Override
    public Optional<Wallet> readWallet(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read wallet data from file: " + filePath);
        return walletStorage.readWallet(filePath);
    }

    @Override
    public void saveWallet(Wallet wallet) throws IOException {
        saveWallet(wallet, walletStorage.getWalletFilePath());
    }

    @Override
    public void saveWallet(Wallet wallet, Path filePath) throws IOException {
        logger.fine("Attempting to write wallet to data file: " + filePath);
        walletStorage.saveWallet(wallet, filePath);
    }

    // =============== Purchase History methods ========================
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

    // =============== AliasList methods ========================

    @Override
    public Path getAliasFilePath() {
        return aliasStorage.getAliasFilePath();
    }

    @Override
    public Optional<AliasList> readList() throws DataConversionException, IOException {
        return readList(aliasStorage.getAliasFilePath());
    }

    @Override
    public Optional<AliasList> readList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read alias pairs from file: " + filePath);
        return aliasStorage.readList(filePath);
    }

    @Override
    public void saveList(AliasList aliasList) throws IOException {
        saveList(aliasList, aliasStorage.getAliasFilePath());
    }

    @Override
    public void saveList(AliasList aliasList, Path filePath) throws IOException {
        logger.fine("Attempting to write alias pairs to data file: " + filePath);
        aliasStorage.saveList(aliasList, filePath);
    }

    // =============== Savings methods ========================

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
