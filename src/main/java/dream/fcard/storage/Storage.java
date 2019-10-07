package dream.fcard.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import dream.fcard.model.ReadOnlyAddressBook;
import dream.fcard.model.ReadOnlyUserPrefs;
import dream.fcard.model.UserPrefs;
import dream.fcard.commons.exceptions.DataConversionException;

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
