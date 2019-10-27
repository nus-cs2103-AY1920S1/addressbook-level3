package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEvents;

/**
 * Represents a storage for {@link seedu.address.model.performance}
 */
public interface EventStorage {

    Path getEventFilePath();

    Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException;

    Optional<ReadOnlyEvents> readEvents(Path filePath) throws DataConversionException, IOException;

    void saveEvents(ReadOnlyEvents events) throws IOException;

    void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException;

}
