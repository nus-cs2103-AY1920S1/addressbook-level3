package budgetbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.ReadOnlyAddressBook;
import budgetbuddy.model.ReadOnlyUserPrefs;
import budgetbuddy.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, LoansStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Path getLoansFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void saveLoans(LoansManager loansManager) throws IOException;

}
