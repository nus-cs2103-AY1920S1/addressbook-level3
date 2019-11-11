package seedu.deliverymans.storage.customer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;

/**
 * Represents a storage for {@link seedu.deliverymans.model.database.CustomerDatabase}.
 */
public interface CustomerDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCustomerDatabaseFilePath();

    /**
     * Returns CustomerDatabase data as a {@link ReadOnlyCustomerDatabase}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCustomerDatabase> readCustomerDatabase() throws DataConversionException, IOException;


    /**
     * @see #getCustomerDatabaseFilePath()
     */
    Optional<ReadOnlyCustomerDatabase> readCustomerDatabase(Path filePath) throws DataConversionException, IOException;


    /**
     * Saves the given {@link ReadOnlyCustomerDatabase} to the storage.
     *
     * @param customerDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase) throws IOException;

    /**
     * @see #saveCustomerDatabase(ReadOnlyCustomerDatabase)
     */
    void saveCustomerDatabase(ReadOnlyCustomerDatabase customerDatabase, Path filePath) throws IOException;

}
