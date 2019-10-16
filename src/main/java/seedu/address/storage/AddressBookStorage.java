package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPatientAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readPatientAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getPatientAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readPatientAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePatientAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #savePatientAddressBook(ReadOnlyAddressBook)
     */
    void savePatientAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
