package calofit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.ReadOnlyAddressBook;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
