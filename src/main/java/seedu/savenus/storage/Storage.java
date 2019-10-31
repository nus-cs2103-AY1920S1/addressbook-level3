package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
 * API of the Storage component
 */
public interface Storage extends MenuStorage, UserPrefsStorage,
        RecsStorage, PurchaseHistoryStorage, CustomSortStorage, SavingsStorage, WalletStorage, AliasStorage {

    // ============================= User Preferences Methods ================================================
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ============================= Menu Methods ================================================
    @Override
    Path getMenuFilePath();

    @Override
    Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException;

    @Override
    void saveMenu(ReadOnlyMenu menu) throws IOException;

    // ============================= Recommendation System Methods ================================================
    @Override
    Path getRecsFilePath();

    @Override
    Optional<UserRecommendations> readRecs() throws DataConversionException, IOException;

    @Override
    Optional<UserRecommendations> readRecs(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveRecs(UserRecommendations recs) throws IOException;

    @Override
    void saveRecs(UserRecommendations recs, Path filePath) throws IOException;

    // ============================= Purchase History Methods ================================================

    @Override
    Path getPurchaseHistoryFilePath();

    @Override
    Optional<ReadOnlyPurchaseHistory> readPurchaseHistory() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyPurchaseHistory> readPurchaseHistory(Path filePath) throws DataConversionException, IOException;

    @Override
    void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) throws IOException;

    @Override
    void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory, Path filePath) throws IOException;

    // ============================= Wallet Methods ================================================

    @Override
    Path getWalletFilePath();

    @Override
    Optional<Wallet> readWallet() throws DataConversionException, IOException;

    @Override
    Optional<Wallet> readWallet(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveWallet(Wallet wallet) throws IOException;

    @Override
    void saveWallet(Wallet wallet, Path filePath) throws IOException;

    // ============================= Custom Sort Methods ================================================

    Path getSortFilePath();

    @Override
    Optional<CustomSorter> readFields() throws DataConversionException, IOException;

    @Override
    Optional<CustomSorter> readFields(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveFields(CustomSorter sorter) throws IOException;

    @Override
    void saveFields(CustomSorter sorter, Path filePath) throws IOException;

    // ============================= Alias List Methods =====================================================

    Path getAliasFilePath();

    @Override
    Optional<AliasList> readList() throws DataConversionException, IOException;

    @Override
    Optional<AliasList> readList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveList(AliasList list) throws IOException;

    @Override
    void saveList(AliasList list, Path filePath) throws IOException;

    // ============================= Savings Account Methods ================================================
    @Override
    Path getSavingsAccountFilePath();

    @Override
    void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount) throws IOException;

    @Override
    void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException;

    Optional<ReadOnlySavingsAccount> readSavingsAccount() throws DataConversionException, IOException;
}
