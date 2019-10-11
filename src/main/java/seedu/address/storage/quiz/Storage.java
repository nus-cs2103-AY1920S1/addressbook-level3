package seedu.address.storage.quiz;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.quiz.ReadOnlyAddressBook;
import seedu.address.model.quiz.ReadOnlyUserPrefs;
import seedu.address.model.quiz.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
