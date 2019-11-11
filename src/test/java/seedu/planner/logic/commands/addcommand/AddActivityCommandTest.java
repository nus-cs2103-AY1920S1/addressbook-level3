package seedu.planner.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.ReadOnlyUserPrefs;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Name;
import seedu.planner.testutil.activity.ActivityBuilder;
import seedu.planner.testutil.activity.TypicalActivity;

public class AddActivityCommandTest {

    @Test
    public void constructor_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddActivityCommand((Activity) null, false));
    }

    @Test
    public void execute_activityAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validActivity = new ActivityBuilder().build();

        CommandResult commandResult = new AddActivityCommand(validActivity, false).execute(modelStub);

        assertEquals(String.format(AddActivityCommand.MESSAGE_SUCCESS, validActivity),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validActivity), modelStub.activitiesAdded);
    }

    @Test
    public void execute_duplicateActivity_throwsCommandException() {
        Activity validActivity = new ActivityBuilder().build();
        AddActivityCommand addActivityCommand = new AddActivityCommand(validActivity, false);
        ModelStub modelStub = new ModelStubWithActivity(validActivity);

        assertThrows(CommandException.class, AddActivityCommand.MESSAGE_DUPLICATE_ACTIVITY, () ->
                addActivityCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Activity activity1 = TypicalActivity.ACTIVITY_A;
        Activity activity2 = TypicalActivity.ACTIVITY_B;

        AddActivityCommand addActivity1Command = new AddActivityCommand(activity1, false);
        AddActivityCommand addActivity2Command = new AddActivityCommand(activity2, false);

        //same object -> returns true
        assertTrue(addActivity1Command.equals(addActivity1Command));

        //same values -> returns true
        AddActivityCommand addActivity1CommandCopy = new AddActivityCommand(activity1, false);
        assertTrue(addActivity1Command.equals(addActivity1CommandCopy));

        //different types -> returns false
        assertFalse(addActivity1Command.equals(1));

        //null -> returns false
        assertFalse(addActivity1Command.equals(null));

        //different activities -> returns false
        assertFalse(addActivity1Command.equals(addActivity2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        // ACCOMMODATION METHODS
        @Override
        public Path getAccommodationFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAccommodationFilePath(Path accommodationFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAccommodation(Accommodation accommodation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAccommodationAtIndex(Index index, Accommodation accommodation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAccommodation(Accommodation accommodation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Index> getAccommodationIndex(Accommodation accommodation) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void deleteAccommodation(Accommodation target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAccommodation(Accommodation target, Accommodation editedAccommodation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAccommodations(ReadOnlyAccommodation accommodations) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAccommodation getAccommodations() {
            throw new AssertionError("This method should not be called.");
        }

        // ACTIVITY METHODS
        @Override
        public Path getActivityFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActivityFilePath(Path activityFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActivities(ReadOnlyActivity activities) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyActivity getActivities() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addActivity(Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addActivityAtIndex(Index index, Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasActivity(Activity activity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Index> getActivityIndex(Activity activity) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void deleteActivity(Activity target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActivity(Activity target, Activity editedActivity) {
            throw new AssertionError("This method should not be called.");
        }

        // CONTACT METHODS
        @Override
        public Path getContactFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContactFilePath(Path contactFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContacts(ReadOnlyContact contacts) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyContact getContacts() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContactAtIndex(Index index, Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Index> getContactIndex(Contact contact) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public boolean hasPhone(Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Contact> getContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        // FOLDER METHODS
        @Override
        public Path getPlannerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPlannerFilePath(Path plannerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        // DAY METHODS
        @Override
        public Path getItineraryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItineraryFilePath(Path itineraryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItinerary(ReadOnlyItinerary itinerary) {
            throw new AssertionError("This method should not be called.");
        }

        public ReadOnlyItinerary getItinerary() {
            throw new AssertionError("This method should not be called.");
        }

        public Name getName() {
            throw new AssertionError("This method should not be called.");
        }

        public void setItineraryName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        public LocalDate getStartDate() {
            throw new AssertionError("This method should not be called.");
        }

        public LocalDateTime getLastDateTime() {
            throw new AssertionError("This method should not be called.");
        }

        public void setItineraryStartDate(LocalDate date) {
            throw new AssertionError("This method should not be called.");
        }

        public void shiftDatesInItineraryByDay(long days) {
            throw new AssertionError("This method should not be called.");
        }

        public void shiftDatesInItineraryByDayBetweenRange(long days, Index startIndex, Index endIndex) {
            throw new AssertionError("This method should not be called.");
        }

        public void addDays(int n) {
            throw new AssertionError("This method should not be called.");
        }

        public void addDayAtIndex(Index index, Day d) {
            throw new AssertionError("This method should not be called.");
        }

        public void deleteDay(Day target) {
            throw new AssertionError("This method should not be called.");
        }

        public void deleteDays(int n) {
            throw new AssertionError("This method should not be called.");
        }

        public void setDays(List<Day> days) {
            throw new AssertionError("This method should not be called.");
        }

        public void setDay(Day oldDay, Day newDay) {
            throw new AssertionError("This method should not be called.");
        }

        public Day getDay(Index index) {
            throw new AssertionError("This method should not be called");
        }

        public int getNumberOfDays() {
            throw new AssertionError("This method should not be called.");
        }

        public void scheduleActivity(Day day, ActivityWithTime toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        public void unscheduleActivity(Day day, Index toRemove) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasDay(Day day) {
            throw new AssertionError("This method should not be called");
        }

        public ObservableList<Day> getFilteredItinerary() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredItinerary(Predicate<Day> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // FILTERED LIST METHODS
        @Override
        public ObservableList<Accommodation> getFilteredAccommodationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAccommodationList(Predicate<Accommodation> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single activity.
     */
    private class ModelStubWithActivity extends ModelStub {
        private final Activity activity;

        ModelStubWithActivity(Activity activity) {
            requireNonNull(activity);
            this.activity = activity;
        }

        @Override
        public boolean hasActivity(Activity activity) {
            requireNonNull(activity);
            return this.activity.isSameActivity(activity);
        }
    }

    /**
     * A Model stub that always accept the activities being added.
     */
    private class ModelStubAcceptingActivityAdded extends ModelStub {
        final ArrayList<Activity> activitiesAdded = new ArrayList<>();
        final ArrayList<Contact> contactsAdded = new ArrayList<>();

        @Override
        public boolean hasActivity(Activity activity) {
            requireNonNull(activity);
            return activitiesAdded.stream().anyMatch(activity::isSameActivity);
        }

        @Override
        public Optional<Index> getActivityIndex(Activity activity) {
            requireNonNull(activity);
            return Optional.of(Index.fromZeroBased(activitiesAdded.indexOf(activity)));
        }

        @Override
        public void addActivity(Activity activity) {
            requireNonNull(activity);
            activitiesAdded.add(activity);
        }

        @Override
        public ReadOnlyActivity getActivities() {
            return new ActivityManager();
        }

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return contactsAdded.stream().anyMatch(contact::isSameContact);
        }

        @Override
        public Optional<Index> getContactIndex(Contact contact) {
            requireNonNull(contact);
            return Optional.of(Index.fromZeroBased(contactsAdded.indexOf(contact)));
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            contactsAdded.add(contact);
        }

        @Override
        public ReadOnlyContact getContacts() {
            return new ContactManager();
        }
    }
}
