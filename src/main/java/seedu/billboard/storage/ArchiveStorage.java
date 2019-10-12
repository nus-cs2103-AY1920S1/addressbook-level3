package seedu.billboard.storage;

import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link Billboard} to store archived expenses.
 */
public interface ArchiveStorage {
    /**
     * Returns the file path of the archive file.
     */
    Path getArchiveFilePath();

    /**
     * Returns Billboard data as a {@link ReadOnlyBillboard}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBillboard> readArchive() throws DataConversionException, IOException;

    /**
     * @see #getArchiveFilePath()
     */
    Optional<ReadOnlyBillboard> readArchive(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBillboard} to the storage.
     * @param archive cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchive(ReadOnlyBillboard archive) throws IOException;

    /**
     * @see #saveArchive(ReadOnlyBillboard)
     */
    void saveArchive(ReadOnlyBillboard archive, Path filePath) throws IOException;
}
