package seedu.planner.storage.day;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.commons.util.JsonUtil;
import seedu.planner.model.ReadOnlyItinerary;

/**
 * A class to access Itinerary data stored as a json file on the hard disk.
 */
public class JsonItineraryStorage implements ItineraryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonItineraryStorage.class);

    private Path filePath;

    public JsonItineraryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getItineraryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException {
        return readItinerary(filePath);
    }

    /**
     * Similar to {@link #readItinerary}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyItinerary> readItinerary(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableItinerary> jsonItinerary = JsonUtil.readJsonFile(
                filePath, JsonSerializableItinerary.class);
        if (!jsonItinerary.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonItinerary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveItinerary(ReadOnlyItinerary itinerary) throws IOException {
        saveItinerary(itinerary, filePath);
    }

    /**
     * Similar to {@link #saveItinerary(ReadOnlyItinerary)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveItinerary(ReadOnlyItinerary itinerary, Path filePath) throws IOException {
        requireNonNull(itinerary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableItinerary(itinerary), filePath);
    }

}
