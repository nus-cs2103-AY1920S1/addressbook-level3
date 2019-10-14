package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.phone.Phone;

/**
 * Represents a storage for {@link seedu.address.model.PhoneBook}.
 */
public interface PhoneBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPhoneBookFilePath();

    /**
     * Returns PhoneBook data as a {@link ReadOnlyDataBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDataBook<Phone>> readPhoneBook() throws DataConversionException, IOException;

    /**
     * @see #getPhoneBookFilePath()
     */
    Optional<ReadOnlyDataBook<Phone>> readPhoneBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDataBook} to the storage.
     * @param phoneBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePhoneBook(ReadOnlyDataBook<Phone> phoneBook) throws IOException;

    /**
     * @see #savePhoneBook(ReadOnlyDataBook)
     */
    void savePhoneBook(ReadOnlyDataBook<Phone> phoneBook, Path filePath) throws IOException;

}
