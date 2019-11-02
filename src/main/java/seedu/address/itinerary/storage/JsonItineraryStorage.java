package seedu.address.itinerary.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.itinerary.model.ReadOnlyItinerary;

/**
 * TravEzy itineerary storage which keeps track of data in a json file.
 */
public class JsonItineraryStorage implements ItineraryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonItineraryStorage.class);

    private Path filePath;

    public JsonItineraryStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Read the current json file for itinerary which contains the events for the itinerary.
     * @return the stored data of the events in the event list of the itinerary.
     * @throws DataConversionException if the data is corrupted in the json file.
     * @throws IOException if the data contains invalid input or output.
     */
    public Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableItinerary> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableItinerary.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Write the changes made to the itinerary.
     * @param itinerary readable but non-editable itinerary.
     * @throws IOException thrown when an invalid input or output is given.
     */
    public void saveItinerary(ReadOnlyItinerary itinerary) throws IOException {
        requireNonNull(itinerary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableItinerary(itinerary), filePath);
    };
}
