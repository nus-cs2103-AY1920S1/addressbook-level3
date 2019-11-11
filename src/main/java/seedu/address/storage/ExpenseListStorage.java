package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;

/**
 * Represents a storage for {@link ExpenseList}.
 */
public interface ExpenseListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExpenseListFilePath();

    /**
     * Returns ExpenseList data as a {@link ReadOnlyExpenseList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpenseList> readExpenseList() throws DataConversionException, IOException;

    /**
     * @see #getExpenseListFilePath()
     */
    Optional<ReadOnlyExpenseList> readExpenseList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExpenseList} to the storage.
     *
     * @param expenseList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExpenseList(ReadOnlyExpenseList expenseList) throws IOException;

    /**
     * @see #saveExpenseList(ReadOnlyExpenseList)
     */
    void saveExpenseList(ReadOnlyExpenseList expenseList, Path filePath) throws IOException;
}
