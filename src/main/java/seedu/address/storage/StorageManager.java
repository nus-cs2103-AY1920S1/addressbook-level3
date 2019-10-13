package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Itinerary data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ItineraryStorage itineraryStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ItineraryStorage itineraryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.itineraryStorage = itineraryStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Itinerary methods ==============================

    @Override
    public Path getItineraryFilePath() {
        return itineraryStorage.getItineraryFilePath();
    }

    @Override
    public Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException {
        return readItinerary(itineraryStorage.getItineraryFilePath());
    }

    @Override
    public Optional<ReadOnlyItinerary> readItinerary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return itineraryStorage.readItinerary(filePath);
    }

    @Override
    public void saveItinerary(ReadOnlyItinerary itinerary) throws IOException {
        saveItinerary(itinerary, itineraryStorage.getItineraryFilePath());
    }

    @Override
    public void saveItinerary(ReadOnlyItinerary itinerary, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        itineraryStorage.saveItinerary(itinerary, filePath);
    }

}
