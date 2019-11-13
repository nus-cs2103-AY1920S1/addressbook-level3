package seedu.planner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
 * API of the Storage component
 */
public interface Storage extends AccommodationStorage, ActivityStorage, ContactStorage, ItineraryStorage,
        UserPrefsStorage {

    // ================ UserPrefs methods ==============================

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ================ AccommodationStorage methods ==============================

    @Override
    Path getAccommodationFilePath();

    //@@author ernestyyh
    @Override
    void setAccommodationFilePath(Path accommodationFilePath);

    //@@author OneArmyj
    @Override
    Optional<ReadOnlyAccommodation> readAccommodation() throws DataConversionException, IOException;

    @Override
    void saveAccommodation(ReadOnlyAccommodation accommodation) throws IOException;

    // ================ ActivityStorage methods ==============================

    @Override
    Path getActivityFilePath();

    //@@author ernestyyh
    @Override
    void setActivityFilePath(Path activityFilePath);

    //@@author OneArmyj
    @Override
    Optional<ReadOnlyActivity> readActivity() throws DataConversionException, IOException;

    @Override
    void saveActivity(ReadOnlyActivity activity) throws IOException;

    // ================ ContactStorage methods ==============================

    @Override
    Path getContactFilePath();

    //@@author ernestyyh
    @Override
    void setContactFilePath(Path contactFilePath);

    //@@author OneArmyj
    @Override
    Optional<ReadOnlyContact> readContact() throws DataConversionException, IOException;

    @Override
    void saveContact(ReadOnlyContact contact) throws IOException;

    // ================ ItineraryStorage methods ==============================

    @Override
    Path getItineraryFilePath();

    //@@author ernestyyh
    @Override
    void setItineraryFilePath(Path itineraryFilePath);

    //@@author OneArmyj
    @Override
    Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException;

    @Override
    void saveItinerary(ReadOnlyItinerary itinerary) throws IOException;

    // ================ PlannerFilePath methods ==============================

    //@@author ernestyyh
    /**
     * Deletes the user prefs' planner file path.
     */
    void deletePlannerFilePath();
}
