package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.ReadOnlyPurchaseHistory;

/**
 * Represents a storage for {@link PurchaseHistory}.
 */
public interface PurchaseHistoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPurchaseHistoryFilePath();
    /**
     * Returns PurchaseHistory data as a {@link ReadOnlyPurchaseHistory}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPurchaseHistory> readPurchaseHistory() throws DataConversionException, IOException;

    /**
     * @see #getPurchaseHistoryFilePath()
     */
    Optional<ReadOnlyPurchaseHistory> readPurchaseHistory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@code ReadOnlyPurchaseHistory} to the storage.
     * @param purchaseHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) throws IOException;

    /**
     * @see #savePurchaseHistory(ReadOnlyPurchaseHistory)
     */
    void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory, Path filePath) throws IOException;

}
