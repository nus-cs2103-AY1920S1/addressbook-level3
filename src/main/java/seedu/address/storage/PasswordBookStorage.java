package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyPasswordBook;

/**
 * Represents a storage for {@link seedu.address.model.PasswordBook}.
 */
public interface PasswordBookStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getPasswordBookFilePath();

    /**
     * Returns PasswordBook data as a {@link PasswordBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPasswordBook> readPasswordBook() throws DataConversionException, IOException;

    /**
     * @see #getPasswordBookFilePath()
     */
    Optional<ReadOnlyPasswordBook> readPasswordBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link PasswordBook} to the storage.
     * @param passwordBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePasswordBook(ReadOnlyPasswordBook passwordBook) throws IOException;

    /**
     * @see #savePasswordBook(ReadOnlyPasswordBook)
     */
    void savePasswordBook(ReadOnlyPasswordBook passwordBook, Path filePath) throws IOException;

}
