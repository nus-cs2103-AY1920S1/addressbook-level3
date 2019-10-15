package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ActivityBook;
import seedu.address.model.InternalState;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage,
        UserPrefsStorage,
        InternalStateStorage,
        ActivityBookStorage {

    @Override
    Optional<InternalState> readInternalState() throws DataConversionException, IOException;

    @Override
    void saveInternalState(InternalState state) throws IOException;

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

    @Override
    Path getActivityBookFilePath();

    @Override
    Optional<ActivityBook> readActivityBook() throws DataConversionException, IOException;

    @Override
    void saveActivityBook(ActivityBook activityBook) throws IOException;

}
