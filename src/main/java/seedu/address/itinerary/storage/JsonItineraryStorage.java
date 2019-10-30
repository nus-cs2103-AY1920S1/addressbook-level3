package seedu.address.itinerary.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.itinerary.model.ReadOnlyItinerary;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class JsonItineraryStorage implements ItineraryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonItineraryStorage.class);

    private Path filePath;

    public JsonItineraryStorage(Path filePath) {
        this.filePath = filePath;
    }

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

    public void saveItinerary(ReadOnlyItinerary itinerary) throws IOException {
        requireNonNull(itinerary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableItinerary(itinerary), filePath);
    };
}
