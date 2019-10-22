package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.IncidentManager;
import seedu.address.model.ReadOnlyIncidentManager;

/**
 * Represents a storage for {@link IncidentManager}.
 */
public interface IncidentManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns IncidentManager data as a {@link ReadOnlyIncidentManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyIncidentManager> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyIncidentManager> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyIncidentManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyIncidentManager addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyIncidentManager)
     */
    void saveAddressBook(ReadOnlyIncidentManager addressBook, Path filePath) throws IOException;

}
