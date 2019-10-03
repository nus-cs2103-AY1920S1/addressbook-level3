package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModulesInfo;

/**
 * Represents a storage for {@link seedu.address.model.ModulesInfo}.
 */
public interface ModulesInfoStorage {

    /**
     * Returns the file path of the ModulesInfo data file.
     */
    Path getModulesInfoPath();

    /**
     * Returns ModulesInfo data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ModulesInfo> readModulesInfo() throws DataConversionException, IOException;
}
