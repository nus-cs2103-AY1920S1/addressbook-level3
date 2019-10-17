package seedu.address.storage.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.ReadOnlyModulo;
import seedu.address.model.cap.ReadOnlyUserPrefs;
import seedu.address.model.cap.UserPrefs;


/**
 * API of the Storage component
 */
public interface Storage extends CapStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyModulo> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyModulo addressBook) throws IOException;

}
