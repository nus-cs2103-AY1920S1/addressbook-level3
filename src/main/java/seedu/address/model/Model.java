package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.Day;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Accommodation> PREDICATE_SHOW_ALL_ACCOMMODATIONS = unused -> true;
    Predicate<Activity> PREDICATE_SHOW_ALL_ACTIVITIES = unused -> true;
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getPlannerFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setPlannerFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code planner}.
     */
    void setPlanner(ReadOnlyPlanner planner);

    /** Returns the Planner */
    ReadOnlyPlanner getPlanner();

    // ACCOMMODATION METHODS
    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    boolean hasAccommodation(Accommodation accommodation);

    /**
     * Deletes the given contacts.
     * The contacts must exist in the address book.
     */
    void deleteAccommodation(Accommodation target);

    /**
     * Adds the given contacts.
     * {@code contacts} must not already exist in the address book.
     */
    void addAccommodation(Accommodation accommodation);

    /**
     * Replaces the given contacts {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the
     * address book.
     */
    void setAccommodation(Accommodation target, Accommodation editedAccommodation);

    /** Returns an unmodifiable view of the filtered contacts list */
    ObservableList<Accommodation> getFilteredAccommodationList();

    /**
     * Updates the filter of the filtered contacts list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAccommodationList(Predicate<Accommodation> predicate);

    // ACTIVITY METHODS
    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    boolean hasActivity(Activity activity);

    /**
     * Deletes the given contacts.
     * The contacts must exist in the address book.
     */
    void deleteActivity(Activity target);

    /**
     * Adds the given contacts.
     * {@code contacts} must not already exist in the address book.
     */
    void addActivity(Activity activity);

    /**
     * Replaces the given contacts {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the
     * address book.
     */
    void setActivity(Activity target, Activity editedActivity);

    /** Returns an unmodifiable view of the filtered contacts list */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Updates the filter of the filtered contacts list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivityList(Predicate<Activity> predicate);

    // CONTACT METHODS
    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contacts.
     * The contacts must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contacts.
     * {@code contacts} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contacts {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the
     * address book.
     */
    void setContact(Contact target, Contact editedContact);

    /** Returns an unmodifiable view of the filtered contacts list */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contacts list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

    // DAY METHODS

    void deleteDay(int n);

    void addDays(int n);

    void setDays(List<Day> days);

    void setDays(int n);
}
