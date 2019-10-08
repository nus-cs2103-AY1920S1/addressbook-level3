package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Contact;
import seedu.address.model.ReadOnlyContact;

/**
 * Represents a storage for {@link Contact}.
 */
public interface FinSecStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFinSecFilePath();

    /**
     * Returns Contact data as a {@link ReadOnlyContact}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyContact> readContacts() throws DataConversionException, IOException;

    /**
     * @see #getFinSecFilePath()
     */
    Optional<ReadOnlyContact> readContacts(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyContact} to the storage.
     * @param contact cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFinSec(ReadOnlyContact contact) throws IOException;

    /**
     * @see #saveFinSec(ReadOnlyContact)
     */
    void saveFinSec(ReadOnlyContact contact, Path filePath) throws IOException;

}
