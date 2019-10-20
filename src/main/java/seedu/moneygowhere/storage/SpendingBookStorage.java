package seedu.moneygowhere.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.SpendingBook;

/**
 * Represents a storage for {@link SpendingBook}.
 */
public interface SpendingBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSpendingBookFilePath();

    /**
     * Returns SpendingBook data as a {@link ReadOnlySpendingBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySpendingBook> readSpendingBook() throws DataConversionException, IOException;

    /**
     * @see #getSpendingBookFilePath()
     */
    Optional<ReadOnlySpendingBook> readSpendingBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySpendingBook} to the storage.
     * @param spendingBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSpendingBook(ReadOnlySpendingBook spendingBook) throws IOException;

    /**
     * @see #saveSpendingBook(ReadOnlySpendingBook)
     */
    void saveSpendingBook(ReadOnlySpendingBook spendingBook, Path filePath) throws IOException;

}
