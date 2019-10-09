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
    public Path getAddressBookFilePath() {
        return itineraryStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyItinerary> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(itineraryStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyItinerary> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return itineraryStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyItinerary addressBook) throws IOException {
        saveAddressBook(addressBook, itineraryStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyItinerary addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        itineraryStorage.saveAddressBook(addressBook, filePath);
    }

}
