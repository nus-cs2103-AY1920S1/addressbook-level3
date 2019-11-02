package seedu.address.itinerary.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.itinerary.model.ReadOnlyItinerary;

/**
 * Interface for the itinerary storage for reading and writing of the json file.
 */
public interface ItineraryStorage {
    Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException;

    void saveItinerary(ReadOnlyItinerary itinerary) throws IOException;
}
