package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link CentralManager}.
 */
public interface CentralManagerStorage {

    Path getManagerFilePath();

    Optional<CentralManager> readManager() throws DataConversionException;

    Optional<CentralManager> readManager(Path filePath) throws DataConversionException;

    void saveManager(CentralManager centralManager) throws IOException;

    void saveManager(CentralManager centralManager, Path filePath) throws IOException;


}
