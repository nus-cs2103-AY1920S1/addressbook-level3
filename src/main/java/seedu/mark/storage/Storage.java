package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;

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
    Optional<ReadOnlyBookmarkManager> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyBookmarkManager addressBook) throws IOException;

}
