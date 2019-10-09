package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
    Path getTravelPalFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTravelPalFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code travelPal}.
     */
    void setTravelPal(ReadOnlyTravelPal travelPal);

    /** Returns the TravelPal */
    ReadOnlyTravelPal getTravelPal();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Sets {@link PageStatus} with an edited version.
     *
     * @param editedPageStatus Edited version of {@link PageStatus}
     */
    void setPageStatus(PageStatus editedPageStatus);

    /**
     * Returns {@link PageStatus} in TravelPal.
     * @return {@link PageStatus} of TravelPal
     */
    PageStatus getPageStatus();

    /**
     * Adds a {@link Trip} to TravelPal.
     *
     * @param trip {@link Trip} to be added
     * @throws ClashingTripException Thrown when {@link Trip} with overlapping duration is added.
     */
    void addTrip(Trip trip) throws ClashingTripException;

    /**
     * Replaces an existing {@link Trip} with another.
     *
     * @param target {@link Trip} set for replacement
     * @param replacement Replacement {@link Trip}
     * @throws ClashingTripException Thrown when {@link Trip} with overlapping duration is set
     * @throws TripNotFoundException Thrown when {@code target} cannot be found
     */
    void setTrip(Trip target, Trip replacement) throws ClashingTripException, TripNotFoundException;

    /**
     * Deletes an existing {@link Trip}.
     * @param target {@link Trip} set for deletion
     * @throws TripNotFoundException Thrown when {@code target} cannot be found
     */
    void deleteTrip(Trip target) throws TripNotFoundException;

    /** Returns an unmodifiable view of the filtered trip list */
    FilteredList<Trip> getFilteredTripList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
