package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BudgetList;
import seedu.address.model.ReadOnlyBudgetList;

/**
 * Represents a storage for {@link BudgetList}.
 */
public interface BudgetListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBudgetListFilePath();

    /**
     * Returns BudgetList data as a {@link ReadOnlyBudgetList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBudgetList> readBudgetList() throws DataConversionException, IOException;

    /**
     * @see #getBudgetListFilePath()
     */
    Optional<ReadOnlyBudgetList> readBudgetList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBudgetList} to the storage.
     *
     * @param budgetList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBudgetList(ReadOnlyBudgetList budgetList) throws IOException;

    /**
     * @see #saveBudgetList(ReadOnlyBudgetList)
     */
    void saveBudgetList(ReadOnlyBudgetList budgetList, Path filePath) throws IOException;
}
