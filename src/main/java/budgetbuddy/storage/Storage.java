package budgetbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.ReadOnlyUserPrefs;
import budgetbuddy.model.UserPrefs;
import budgetbuddy.storage.loans.LoansStorage;

/**
 * API of the Storage component
 */
public interface Storage extends LoansStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getLoansFilePath();

    @Override
    void saveLoans(LoansManager loansManager) throws IOException;

}
