package seedu.address.storage.catalogue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCatalogue;

/**
 * Represents a storage for {@link seedu.address.model.Catalogue}.
 */
public interface CatalogueStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCatalogueFilePath();

    /**
     * Returns Catalogue data as a {@link ReadOnlyCatalogue}.
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
     * @param Catalogue cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCatalogue(ReadOnlyCatalogue Catalogue) throws IOException;

    /**
     * @see #saveCatalogue(ReadOnlyCatalogue)
     */
    void saveCatalogue(ReadOnlyCatalogue Catalogue, Path filePath) throws IOException;

}
