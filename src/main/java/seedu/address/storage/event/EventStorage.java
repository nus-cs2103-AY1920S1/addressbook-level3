package seedu.address.storage.event;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;

/**
 * Represents a storage for {@link EventRecord}.
 */
public interface EventStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getEventRecordFilePath();

    /**
     * Returns events data as a {@link ReadOnlyEvents}. Returns {@code Optional.empty()}
     * if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException;

    /**
     * @see #getEventRecordFilePath()
     */
    Optional<ReadOnlyEvents> readEvents(Path filePath)
        throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEvents} to the storage.
     *
     * @param events cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEvents(ReadOnlyEvents events) throws IOException;

    /**
     * @see #saveEvents(ReadOnlyEvents)
     */
    void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException;
}
