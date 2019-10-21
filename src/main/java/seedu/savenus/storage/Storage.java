package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyUserPrefs;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.SavingsStorage;
import seedu.savenus.model.sorter.CustomSorter;

/**
 * API of the Storage component
 */
public interface Storage extends MenuStorage, UserPrefsStorage, RecsStorage, CustomSortStorage, SavingsStorage {

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

    // ============================= Custom Sort Methods ================================================
    @Override
    Path getSortFilePath();

    @Override
    Optional<CustomSorter> readFields() throws DataConversionException, IOException;

    @Override
    Optional<CustomSorter> readFields(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveFields(CustomSorter sorter) throws IOException;

    @Override
    void saveFields(CustomSorter sorter, Path filePath) throws IOException;

    // ============================= Savings Account Methods ================================================
    @Override
    Path getSavingsAccountFilePath();

    @Override
    void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount) throws IOException;

    @Override
    void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException;

    Optional<ReadOnlySavingsAccount> readSavingsAccount() throws DataConversionException, IOException;
}
