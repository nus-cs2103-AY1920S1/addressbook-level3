package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;

import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.ReadOnlyUserPrefs;
import seedu.planner.model.UserPrefs;
import seedu.planner.storage.accommodation.AccommodationStorage;
import seedu.planner.storage.activity.ActivityStorage;
import seedu.planner.storage.contact.ContactStorage;
import seedu.planner.storage.day.ItineraryStorage;

//@@author OneArmyj
/**
 * Manages storage of Planner data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AccommodationStorage accommodationStorage;
    private ActivityStorage activityStorage;
    private ContactStorage contactStorage;
    private ItineraryStorage itineraryStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AccommodationStorage accommodationStorage, ActivityStorage activityStorage,
                          ContactStorage contactStorage, ItineraryStorage itineraryStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.accommodationStorage = accommodationStorage;
        this.activityStorage = activityStorage;
        this.contactStorage = contactStorage;
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


    // ================ AccommodationStorage methods ==============================

    @Override
    public Path getAccommodationFilePath() {
        return accommodationStorage.getAccommodationFilePath();
    }

    @Override
    public void setAccommodationFilePath(Path accommodationFilePath) {
        accommodationStorage.setAccommodationFilePath(accommodationFilePath);
    }

    @Override
    public Optional<ReadOnlyAccommodation> readAccommodation() throws DataConversionException, IOException {
        return readAccommodation(accommodationStorage.getAccommodationFilePath());
    }

    @Override
    public Optional<ReadOnlyAccommodation> readAccommodation(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return accommodationStorage.readAccommodation(filePath);
    }

    @Override
    public void saveAccommodation(ReadOnlyAccommodation accommodation) throws IOException {
        saveAccommodation(accommodation, accommodationStorage.getAccommodationFilePath());
    }

    @Override
    public void saveAccommodation(ReadOnlyAccommodation accommodation, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        accommodationStorage.saveAccommodation(accommodation, filePath);
    }

    // ================ ActivityStorage methods ==============================

    @Override
    public Path getActivityFilePath() {
        return activityStorage.getActivityFilePath();
    }

    @Override
    public void setActivityFilePath(Path activityFilePath) {
        activityStorage.setActivityFilePath(activityFilePath);
    }

    @Override
    public Optional<ReadOnlyActivity> readActivity() throws DataConversionException, IOException {
        return readActivity(activityStorage.getActivityFilePath());
    }

    @Override
    public Optional<ReadOnlyActivity> readActivity(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return activityStorage.readActivity(filePath);
    }

    @Override
    public void saveActivity(ReadOnlyActivity activity) throws IOException {
        saveActivity(activity, activityStorage.getActivityFilePath());
    }

    @Override
    public void saveActivity(ReadOnlyActivity activity, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        activityStorage.saveActivity(activity, filePath);
    }

    // ================ ContactStorage methods ==============================

    @Override
    public Path getContactFilePath() {
        return contactStorage.getContactFilePath();
    }

    @Override
    public void setContactFilePath(Path contactFilePath) {
        contactStorage.setContactFilePath(contactFilePath);
    }

    @Override
    public Optional<ReadOnlyContact> readContact() throws DataConversionException, IOException {
        return readContact(contactStorage.getContactFilePath());
    }

    @Override
    public Optional<ReadOnlyContact> readContact(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return contactStorage.readContact(filePath);
    }

    @Override
    public void saveContact(ReadOnlyContact contact) throws IOException {
        saveContact(contact, contactStorage.getContactFilePath());
    }

    @Override
    public void saveContact(ReadOnlyContact contact, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        contactStorage.saveContact(contact, filePath);
    }

    // ================ ItineraryStorage methods ==============================

    @Override
    public Path getItineraryFilePath() {
        return itineraryStorage.getItineraryFilePath();
    }

    @Override
    public void setItineraryFilePath(Path itineraryFilePath) {
        itineraryStorage.setItineraryFilePath(itineraryFilePath);
    }

    @Override
    public Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException {
        return readItinerary(itineraryStorage.getItineraryFilePath());
    }

    @Override
    public Optional<ReadOnlyItinerary> readItinerary(Path filePath) throws DataConversionException,
            IOException {
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

    // ================ PlannerFilePath methods ==============================

    @Override
    public void deletePlannerFilePath() {
        getAccommodationFilePath().toFile().delete();
        getActivityFilePath().toFile().delete();
        getContactFilePath().toFile().delete();
        getItineraryFilePath().toFile().delete();
        getItineraryFilePath().getParent().toFile().delete();
    }
}
