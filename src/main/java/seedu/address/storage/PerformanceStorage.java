package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPerformance;

/**
 * Represents a storage for {@link seedu.address.model.performance}
 */
public interface PerformanceStorage {

    Path getEventFilePath();

    Optional<ReadOnlyPerformance> readEvents() throws DataConversionException, IOException;

    Optional<ReadOnlyPerformance> readEvents(Path filePath) throws DataConversionException, IOException;

    void saveEvents(ReadOnlyPerformance events) throws IOException;

    void saveEvents(ReadOnlyPerformance events, Path filePath) throws IOException;

}
