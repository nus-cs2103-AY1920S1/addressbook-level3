//@@author e0031374
package tagline.storage.group;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tagline.commons.exceptions.DataConversionException;
import tagline.model.group.ReadOnlyGroupBook;

/**
 * Represents a storage for {@link tagline.model.group.GroupBook}.
 */
public interface GroupBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGroupBookFilePath();

    /**
     * Returns GroupBook data as a {@link ReadOnlyGroupBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGroupBook> readGroupBook() throws DataConversionException, IOException;

    /**
     * @see #getGroupBookFilePath()
     */
    Optional<ReadOnlyGroupBook> readGroupBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGroupBook} to the storage.
     *
     * @param groupBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGroupBook(ReadOnlyGroupBook groupBook) throws IOException;

    /**
     * @see #saveGroupBook(ReadOnlyGroupBook)
     */
    void saveGroupBook(ReadOnlyGroupBook groupBook, Path filePath) throws IOException;

}
