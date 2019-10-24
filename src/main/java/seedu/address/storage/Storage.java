package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;

import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.ReadOnlyActivity;
import seedu.address.model.ReadOnlyContact;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.accommodation.AccommodationStorage;
import seedu.address.storage.activity.ActivityStorage;
import seedu.address.storage.contact.ContactStorage;
import seedu.address.storage.day.ItineraryStorage;

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

    @Override
    Optional<ReadOnlyAccommodation> readAccommodation() throws DataConversionException, IOException;

    @Override
    void saveAccommodation(ReadOnlyAccommodation accommodation) throws IOException;

    // ================ ActivityStorage methods ==============================

    @Override
    Path getActivityFilePath();

    @Override
    Optional<ReadOnlyActivity> readActivity() throws DataConversionException, IOException;

    @Override
    void saveActivity(ReadOnlyActivity activity) throws IOException;

    // ================ ContactStorage methods ==============================

    @Override
    Path getContactFilePath();

    @Override
    Optional<ReadOnlyContact> readContact() throws DataConversionException, IOException;

    @Override
    void saveContact(ReadOnlyContact contact) throws IOException;

    // ================ ItineraryStorage methods ==============================

    @Override
    Path getItineraryFilePath();

    @Override
    Optional<ReadOnlyItinerary> readItinerary() throws DataConversionException, IOException;

    @Override
    void saveItinerary(ReadOnlyItinerary itinerary) throws IOException;
}
