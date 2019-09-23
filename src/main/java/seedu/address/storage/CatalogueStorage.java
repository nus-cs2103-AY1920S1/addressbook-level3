package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;

/**
 * Represents a storage for {@link Catalogue}.
 */
public interface CatalogueStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCatalogueFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyCatalogue}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException, IOException;

    /**
     * @see #getCatalogueFilePath()
     */
    Optional<ReadOnlyCatalogue> readCatalogue(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCatalogue} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyCatalogue addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyCatalogue)
     */
    void saveAddressBook(ReadOnlyCatalogue addressBook, Path filePath) throws IOException;

}
