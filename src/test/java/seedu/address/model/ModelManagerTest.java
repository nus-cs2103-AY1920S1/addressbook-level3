package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.contact.TypicalContacts.ALICE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.accommodation.TypicalAccommodations;
import seedu.address.testutil.activity.TypicalActivity;
import seedu.address.testutil.contact.TypicalContacts;
import seedu.address.testutil.day.TypicalDays;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AccommodationManager(), new AccommodationManager(modelManager.getAccommodations()));
        assertEquals(new ActivityManager(), new ActivityManager(modelManager.getActivities()));
        assertEquals(new ContactManager(), new ContactManager(modelManager.getContacts()));
        assertEquals(new Itinerary(), new Itinerary(modelManager.getItinerary()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAccommodationFilePath(Paths.get("accommodation/file/path"));
        userPrefs.setActivityFilePath(Paths.get("activity/file/path"));
        userPrefs.setContactFilePath(Paths.get("contact/file/path"));
        userPrefs.setItineraryFilePath(Paths.get("itinerary/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAccommodationFilePath(Paths.get("new/accommodation/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    //========== ACCOMMODATIONS ========================================================================
    @Test
    public void setAccommodationFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAccommodations(null));
    }

    @Test
    public void setAccommodationFilePath_validPath_setsAccommodationFilePath() {
        Path path = Paths.get("accommodation/file/path");
        modelManager.setAccommodationFilePath(path);
        assertEquals(path, modelManager.getAccommodationFilePath());
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInPlanner_returnsFalse() {
        assertFalse(modelManager.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInPlanner_returnsTrue() {
        modelManager.addContact(ALICE);
        assertTrue(modelManager.hasContact(ALICE));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void equals() {
        AccommodationManager accommodation = TypicalAccommodations.getTypicalAccommodationManager();
        AccommodationManager differentAccommodation = new AccommodationManager();

        ActivityManager activity = TypicalActivity.getTypicalActivityManager();
        ActivityManager differentActivity = new ActivityManager();

        ContactManager contact = TypicalContacts.getTypicalContactManager();
        ContactManager differentContact = new ContactManager();

        Itinerary itinerary = TypicalDays.getTypicalItinerary();
        Itinerary differentItinerary = new Itinerary();

        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(accommodation, activity, contact, itinerary, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(accommodation, activity, contact, itinerary, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different accommodation -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAccommodation, activity, contact,
                itinerary, userPrefs)));

        // different activity -> returns false
        assertFalse(modelManager.equals(new ModelManager(accommodation, differentActivity, contact,
                itinerary, userPrefs)));

        // different contact -> returns false
        assertFalse(modelManager.equals(new ModelManager(accommodation, activity, differentContact,
                itinerary, userPrefs)));

        // different itinerary -> returns false
        assertFalse(modelManager.equals(new ModelManager(accommodation, activity, contact,
                differentItinerary, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().toString().split("\\s+");
        modelManager.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(accommodation, activity, contact, itinerary, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setActivityFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(accommodation, activity, contact,
                itinerary, differentUserPrefs)));
    }
}
