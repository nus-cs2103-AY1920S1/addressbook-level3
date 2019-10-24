package seedu.address.storage.day;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Itinerary;
import seedu.address.model.ReadOnlyItinerary;

/**
 * Represents a storage for {@link Itinerary}.
 */
public interface ItineraryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getItineraryFilePath();

    /**
     * Returns Itinerary data as a {@link ReadOnlyItinerary}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException;

    /**
     * @see #getItineraryFilePath()
     */
    Optional<ReadOnlyItinerary> readItinerary(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyItinerary} to the storage.
     * @param itinerary cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveItinerary(ReadOnlyItinerary itinerary) throws IOException;

    /**
     * @see #saveItinerary(ReadOnlyItinerary)
     */
    void saveItinerary(ReadOnlyItinerary address, Path filePath) throws IOException;

}
