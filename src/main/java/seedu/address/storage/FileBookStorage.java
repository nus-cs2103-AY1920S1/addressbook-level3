package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFileBook;

/**
 * Represents a storage for {@link seedu.address.model.FileBook}.
 */
public interface FileBookStorage {

    /**
     * Returns the password used to store the file book and encrypt files.
     */
    String getStoragePassword();

    /**
     * Returns the file path of the data file.
     */
    Path getFileBookFilePath();

    /**
     * Returns FileBook data as a {@link ReadOnlyFileBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFileBook> readFileBook() throws DataConversionException, IOException;

    /**
     * @see #getFileBookFilePath()
     */
    Optional<ReadOnlyFileBook> readFileBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFileBook} to the storage.
     * @param fileBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFileBook(ReadOnlyFileBook fileBook) throws IOException;

    /**
     * @see #saveFileBook(ReadOnlyFileBook)
     */
    void saveFileBook(ReadOnlyFileBook addressBook, Path filePath) throws IOException;

}

