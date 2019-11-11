package seedu.address.storage.catalog;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Catalog;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;

/**
 * Represents a storage for {@link Catalog}.
 */
public interface CatalogStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCatalogFilePath();

    /**
     * Returns Catalog data as a {@link ReadOnlyCatalog}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param initialLoanRecords Initial {@code ReadOnlyLoanRecords} loaded when ModelManage is initialized.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCatalog> readCatalog(ReadOnlyLoanRecords initialLoanRecords)
            throws DataConversionException, IOException;

    /**
     * @see #getCatalogFilePath()
     */
    Optional<ReadOnlyCatalog> readCatalog(Path filePath, ReadOnlyLoanRecords initialLoanRecords)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCatalog} to the storage.
     * @param catalog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCatalog(ReadOnlyCatalog catalog) throws IOException;

    /**
     * @see #saveCatalog(ReadOnlyCatalog)
     */
    void saveCatalog(ReadOnlyCatalog catalog, Path filePath) throws IOException;

}
