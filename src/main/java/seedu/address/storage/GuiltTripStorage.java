package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.GuiltTrip;
import seedu.address.model.ReadOnlyGuiltTrip;

/**
 * Represents a storage for {@link GuiltTrip}.
 */
public interface GuiltTripStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns GuiltTrip data as a {@link ReadOnlyGuiltTrip}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGuiltTrip> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyGuiltTrip> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGuiltTrip} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyGuiltTrip addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyGuiltTrip)
     */
    void saveAddressBook(ReadOnlyGuiltTrip addressBook, Path filePath) throws IOException;

}
