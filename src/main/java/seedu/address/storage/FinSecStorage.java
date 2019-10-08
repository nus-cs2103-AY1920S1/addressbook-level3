package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FinSec;
import seedu.address.model.ReadOnlyFinSec;

/**
 * Represents a storage for {@link FinSec}.
 */
public interface FinSecStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFinSecFilePath();

    /**
     * Returns FinSec data as a {@link ReadOnlyFinSec}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFinSec> readContacts() throws DataConversionException, IOException;

    /**
     * @see #getFinSecFilePath()
     */
    Optional<ReadOnlyFinSec> readContacts(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFinSec} to the storage.
     * @param contact cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFinSec(ReadOnlyFinSec contact) throws IOException;

    /**
     * @see #saveFinSec(seedu.address.model.ReadOnlyFinSec)
     */
    void saveFinSec(ReadOnlyFinSec contact, Path filePath) throws IOException;

}
