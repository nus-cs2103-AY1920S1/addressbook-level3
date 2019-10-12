package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.RegimeBook;

/**
 * Represents a storage for {@link RegimeBook}.
 */
public interface RegimeBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRegimeBookFilePath();

    /**
     * Returns RegimeBook data as a {@link ReadOnlyRegimeBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRegimeBook> readRegimeBook() throws DataConversionException, IOException;

    /**
     * @see #getRegimeBookFilePath()
     */
    Optional<ReadOnlyRegimeBook> readRegimeBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRegimeBook} to the storage.
     * @param regimeBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRegimeBook(ReadOnlyRegimeBook regimeBook) throws IOException;

    /**
     * @see #saveRegimeBook(ReadOnlyRegimeBook)
     */
    void saveRegimeBook(ReadOnlyRegimeBook regimeBook, Path filePath) throws IOException;
}
