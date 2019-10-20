package budgetbuddy.storage.loans;

import java.io.IOException;
import java.nio.file.Path;

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
