package budgetbuddy.storage.loans;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.LoansManager;

/**
 * Represents a storage for {@link LoansManager}.
 */
public interface LoansStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLoansFilePath();

    /**
     * Returns LoansManager data as a {@link LoansManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException If the data in storage is not in the expected format.
     * @throws IOException If there was any problem when reading from the storage.
     */
    Optional<LoansManager> readLoans() throws DataConversionException, IOException;

    /**
     * @see #readLoans()
     */
    Optional<LoansManager> readLoans(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link LoansManager} to the storage.
     * @param loansManager Cannot be null.
     * @throws IOException If any problem occurs when writing to the file.
     */
    void saveLoans(LoansManager loansManager) throws IOException;

    /**
     * @param filePath The path to save the data file to.
     * @see #saveLoans(LoansManager)
     */
    void saveLoans(LoansManager loansManager, Path filePath) throws IOException;
}
